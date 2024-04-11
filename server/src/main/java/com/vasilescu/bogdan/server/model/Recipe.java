package com.vasilescu.bogdan.server.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
@Data
public class Recipe {
    @Id
    private String id;
    private String name;
    private String description;
    private int preparationTime;
    private int cookingTime;
    private String skillLevel;
    @Relationship(type = "CONTAINS_INGREDIENT", direction = Relationship.Direction.OUTGOING)
    private Set<Ingredient> ingredients;
    @Relationship(type = "WROTE", direction = Relationship.Direction.INCOMING)
    private Author author;
    @Relationship(type = "COLLECTION", direction = Relationship.Direction.OUTGOING)
    private Set<Collection> collection;
    @Relationship(type = "DIET_TYPE", direction = Relationship.Direction.OUTGOING)
    private Set<DietType> dietTypes;
    @Relationship(type = "KEYWORD", direction = Relationship.Direction.OUTGOING)
    private Set<Keyword> keywords;
}
