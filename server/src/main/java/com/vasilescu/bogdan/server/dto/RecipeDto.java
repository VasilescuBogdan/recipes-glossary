package com.vasilescu.bogdan.server.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RecipeDto {
    private String name;
    private String author;
    private int numberOfIngredients;
    private String skillLevel;
}
