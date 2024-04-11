package com.vasilescu.bogdan.server.controller;

import com.vasilescu.bogdan.server.dto.RecipeDto;
import com.vasilescu.bogdan.server.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/page/{page_number}")
    public List<RecipeDto> getRecipePage(@PathVariable("page_number") int pageNumber) {
        return recipeService.getRecipePage(pageNumber);
    }
}
