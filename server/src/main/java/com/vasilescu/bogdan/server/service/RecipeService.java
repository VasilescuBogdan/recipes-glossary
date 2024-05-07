package com.vasilescu.bogdan.server.service;

import com.vasilescu.bogdan.server.dto.RecipeDetailsDto;
import com.vasilescu.bogdan.server.dto.RecipeDto;
import com.vasilescu.bogdan.server.model.Author;
import com.vasilescu.bogdan.server.model.Ingredient;
import com.vasilescu.bogdan.server.model.Recipe;
import com.vasilescu.bogdan.server.repository.AuthorRepository;
import com.vasilescu.bogdan.server.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private static final int PAGE_SIZE = 20;
    private final RecipeRepository recipeRepository;
    private final AuthorRepository authorRepository;

    public List<RecipeDto> getRecipesPage(int pageNumber) {
        List<Recipe> pageContent = recipeRepository.findAll(getPageSetup(pageNumber))
                                                   .getContent();
        return getRepresentation(pageContent);
    }

    public List<RecipeDto> getFilteredPage(int pageNumber, List<String> ingredients) {
        List<Recipe> pageContent = recipeRepository.findRecipesByAllIngredients(ingredients, ingredients.size(),
                                                           getPageSetup(pageNumber))
                                                   .getContent();
        return getRepresentation(pageContent);
    }

    public RecipeDetailsDto getRecipeDetails(String recipeName) {
        Recipe recipe = recipeRepository.findRecipeByName(recipeName);
        return RecipeDetailsDto.builder()
                               .description(recipe.getDescription())
                               .cookingTime(recipe.getCookingTime())
                               .preparationTime(recipe.getPreparationTime())
                               .ingredients(recipe.getIngredients()
                                                  .stream()
                                                  .map(Ingredient::getName)
                                                  .toList())
                               .build();
    }

    public List<RecipeDto> getSearchResult(int pageNumber, String searchKey) {
        List<Recipe> pageContent = recipeRepository.findRecipesByNameContainingIgnoreCase(getPageSetup(pageNumber),
                                                           searchKey)
                                                   .getContent();
        return getRepresentation(pageContent);
    }

    public List<RecipeDto> getRecipeAuthor(int pageNumber, String authorName) {
        Optional<Author> author = authorRepository.findById(authorName);
        List<Recipe> pageContent = new ArrayList<>();
        if (author.isPresent()) {
            pageContent = recipeRepository.findRecipesByAuthorName(getPageSetup(pageNumber), author.get()
                                                                                                   .getName())
                                          .getContent();
        }
        return getRepresentation(pageContent);
    }

    private PageRequest getPageSetup(int pageNumber) {
        return PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("name")
                                                         .ascending());
    }

    private List<RecipeDto> getRepresentation(List<Recipe> pageContent) {
        return pageContent.stream()
                          .map(recipe -> RecipeDto.builder()
                                                  .name(recipe.getName())
                                                  .author(recipe.getAuthor()
                                                                .getName())
                                                  .numberOfIngredients(recipe.getIngredients()
                                                                             .size())
                                                  .skillLevel(recipe.getSkillLevel())
                                                  .build())
                          .toList();
    }
}
