package com.vasilescu.bogdan.server.repository;

import com.vasilescu.bogdan.server.model.Recipe;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
@NonNullApi
public interface RecipeRepository extends Neo4jRepository<Recipe, String> {
    Page<Recipe> findAll(Pageable pageable);
    Recipe findRecipeByName(String name);
}
