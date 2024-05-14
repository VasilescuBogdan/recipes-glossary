package com.vasilescu.bogdan.server.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecipeDetailsDto {
    private String description;
    private int cookingTime;
    private int preparationTime;
    private List<String> ingredients;
    private List<String> collections;
    private List<String> keywords;
    private List<String> diet;
}
