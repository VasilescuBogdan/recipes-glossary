package com.vasilescu.bogdan.server.service;

import com.vasilescu.bogdan.server.model.Ingredient;
import com.vasilescu.bogdan.server.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public List<String> listIngredients() {
        return ingredientRepository.findAll()
                                   .stream()
                                   .map(Ingredient::getName)
                                   .sorted(String.CASE_INSENSITIVE_ORDER)
                                   .toList();
    }
}
