package com.vasilescu.bogdan.server.repository;

import com.vasilescu.bogdan.server.model.Ingredient;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends Neo4jRepository<Ingredient, String> {
}
