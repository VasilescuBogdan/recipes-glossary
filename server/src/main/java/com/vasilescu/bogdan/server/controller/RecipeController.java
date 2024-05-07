package com.vasilescu.bogdan.server.controller;

import com.vasilescu.bogdan.server.dto.RecipeDetailsDto;
import com.vasilescu.bogdan.server.dto.RecipeDto;
import com.vasilescu.bogdan.server.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/recipe")
@CrossOrigin("http://localhost:4200")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/page/{page_number}")
    public List<RecipeDto> getRecipesPage(@PathVariable("page_number") int pageNumber) {
        return recipeService.getRecipesPage(pageNumber);
    }

    @GetMapping("/details/{recipe_name}")
    public RecipeDetailsDto getRecipeDetails(@PathVariable("recipe_name") String recipeName) {
        return recipeService.getRecipeDetails(recipeName);
    }

    @GetMapping("/search")
    public List<RecipeDto> getSearchResults(@RequestParam("search_key") String searchKey,
                                            @RequestParam("page_number") int pageNumber) {
        return recipeService.getSearchResult(pageNumber, searchKey);
    }

    @PostMapping("/filter")
    public List<RecipeDto> getFilterResults(@RequestBody List<String> ingredients,
                                            @RequestParam("page_number") int pageNumber) {
        return recipeService.getFilteredPage(pageNumber, ingredients);
    }

    @GetMapping("/author")
    public List<RecipeDto> getAuthorRecipes(@RequestParam("page_number") int pageNumber,
                                            @RequestParam("author") String authorName) {
        return recipeService.getRecipeAuthor(pageNumber, authorName);
    }
}
