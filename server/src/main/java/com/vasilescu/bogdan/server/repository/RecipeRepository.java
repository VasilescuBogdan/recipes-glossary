package com.vasilescu.bogdan.server.repository;

import com.vasilescu.bogdan.server.model.Recipe;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@NonNullApi
public interface RecipeRepository extends Neo4jRepository<Recipe, String> {
    Page<Recipe> findAll(Pageable pageable);

    Recipe findRecipeByName(String name);

    List<Recipe> findRecipesByNameContainingIgnoreCase(String keyword);

    Page<Recipe> findRecipesByNameContainingIgnoreCase(Pageable pageable, String keyword);

    @Query(value = "MATCH (r:Recipe)-[:CONTAINS_INGREDIENT]->(i:Ingredient) WHERE i.name IN $ingredientNames " +
                   "WITH r, count(i) AS ingredientCount, collect(i.name) AS ingredientNames " +
                   "WHERE ingredientCount = $ingredientCount RETURN r{.cookingTime, .description, .id, .name, " +
                   ".preparationTime, .skillLevel, __nodeLabels__: labels(r), __elementId__: id(r), " +
                   "Recipe_WROTE_Author: [(r)<-[:`WROTE`]-(recipe_author:`Author`) | recipe_author{.name, " +
                   "__nodeLabels__: labels(recipe_author), __elementId__: id(recipe_author)}], " +
                   "Recipe_COLLECTION_Collection: [(r)-[:`COLLECTION`]->(recipe_collection:`Collection`) | " +
                   "recipe_collection{.name, __nodeLabels__: labels(recipe_collection), __elementId__: id" +
                   "(recipe_collection)}], Recipe_DIET_TYPE_DietType: [(r)-[:`DIET_TYPE`]->" +
                   "(recipe_dietTypes:`DietType`) | recipe_dietTypes{.name, __nodeLabels__: labels(recipe_dietTypes)," +
                   " __elementId__: id(recipe_dietTypes)}], Recipe_KEYWORD_Keyword: [(r)-[:`KEYWORD`]->" +
                   "(recipe_keywords:`Keyword`) | recipe_keywords{.name, __nodeLabels__: labels(recipe_keywords), " +
                   "__elementId__: id(recipe_keywords)}], Recipe_CONTAINS_INGREDIENT_Ingredient: [(r)" +
                   "-[:`CONTAINS_INGREDIENT`]->(recipe_ingredients:`Ingredient`) | recipe_ingredients{.name, " +
                   "__nodeLabels__: labels(recipe_ingredients), __elementId__: id(recipe_ingredients)}]} SKIP $skip " +
                   "LIMIT $limit", countQuery =
                                           "MATCH (r:Recipe)-[:CONTAINS_INGREDIENT]->(i:Ingredient) WHERE i.name IN " +
                                           "$ingredientNames WITH r, count(i) AS ingredientCount WHERE " +
                                           "ingredientCount = " + "$ingredientCount RETURN count(r)")
    Page<Recipe> findRecipesByAllIngredients(@Param("ingredientNames") List<String> ingredientNames,
                                             @Param("ingredientCount") int ingredientCount, Pageable pageable);

    @Query(value = "MATCH (r:Recipe)-[:CONTAINS_INGREDIENT]->(i:Ingredient) WHERE i.name IN $ingredientNames " +
                   "WITH r, count(i) AS ingredientCount, collect(i.name) AS ingredientNames " +
                   "WHERE ingredientCount = $ingredientCount RETURN r")
    List<Recipe> findRecipesByAllIngredients(@Param("ingredientNames") List<String> ingredientNames,
                                             @Param("ingredientCount") int ingredientCount);

    Page<Recipe> findRecipesByAuthorName(Pageable pageable, String author);

    List<Recipe> findRecipesByAuthorName(String author);
}
