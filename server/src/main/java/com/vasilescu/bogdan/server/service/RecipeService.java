package com.vasilescu.bogdan.server.service;

import com.vasilescu.bogdan.server.dto.RecipeDetailsDto;
import com.vasilescu.bogdan.server.dto.RecipeDto;
import com.vasilescu.bogdan.server.model.Ingredient;
import com.vasilescu.bogdan.server.model.Recipe;
import com.vasilescu.bogdan.server.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private static final int PAGE_SIZE = 20;
    private static final String EMPTY_SPACES = "\\s+";
    private final RecipeRepository recipeRepository;

    public List<RecipeDto> getRecipesPage(int pageNumber, String searchKey) {
        List<Recipe> pageContent;
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("name").ascending());
        if (Objects.equals(searchKey, "")) {
            pageContent = recipeRepository.findAll(pageable).getContent();
        } else {
            String[] words = searchKey.split(EMPTY_SPACES);
            pageContent = getPageFromList(combineAndSortSearchResults(words), pageNumber);
        }
        return pageContent.stream()
                       .map(recipe -> RecipeDto.builder().name(recipe.getName()).author(recipe.getAuthor().getName())
                                              .numberOfIngredients(recipe.getIngredients().size())
                                              .skillLevel(recipe.getSkillLevel()).build()).toList();
    }

    private List<Recipe> getPageFromList(List<Recipe> list, int pageNumber) {
        int start = pageNumber * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, list.size());
        return list.subList(start, end);
    }

    private List<Recipe> combineAndSortSearchResults(String[] words) {
        List<Recipe> recipeList = recipeRepository.findAll();
        return recipeList.stream().filter(recipe -> calculateScore(recipe, words) != 0)
                       .sorted(Comparator.comparingInt((Recipe recipe) -> calculateScore(recipe, words)).reversed())
                       .toList();
    }

    private int calculateScore(Recipe recipe, String[] words) {
        int score = 0;
        for (String word : words) {
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word) + "\\b", Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(recipe.getName()).find()) {
                score++;
            }
        }
        return score;
    }

    public int getNumberOfPages() {
        Pageable pageable = PageRequest.ofSize(PAGE_SIZE);
        return recipeRepository.findAll(pageable).getTotalPages();
    }

    public RecipeDetailsDto getRecipeDetails(String recipeName) {
        Recipe recipe = recipeRepository.findRecipeByName(recipeName);
        return RecipeDetailsDto.builder().description(recipe.getDescription()).cookingTime(recipe.getCookingTime())
                       .preparationTime(recipe.getPreparationTime())
                       .ingredients(recipe.getIngredients().stream().map(Ingredient::getName).toList()).build();
    }
}
