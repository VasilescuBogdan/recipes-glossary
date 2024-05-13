package com.vasilescu.bogdan.server.controller;

import com.vasilescu.bogdan.server.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping()
    public List<String> getAllIngredients() {
        return ingredientService.listIngredients();
    }
}
