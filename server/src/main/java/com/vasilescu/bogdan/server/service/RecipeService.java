package com.vasilescu.bogdan.server.service;

import com.vasilescu.bogdan.server.dto.RecipeDto;
import com.vasilescu.bogdan.server.model.Recipe;
import com.vasilescu.bogdan.server.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private static final int PAGE_SIZE = 20;
    private final RecipeRepository recipeRepository;

    public List<RecipeDto> getRecipePage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("name").ascending());
        List<Recipe> pageContent = recipeRepository.findAll(pageable)
                                           .getContent();
        return pageContent.stream()
                       .map(recipe -> RecipeDto.builder()
                                              .name(recipe.getName())
                                              .author(recipe.getAuthor().getName())
                                              .numberOfIngredients(recipe.getIngredients().size())
                                              .skillLevel(recipe.getSkillLevel())
                                              .build())
                       .toList();
    }
}