package org.example.recipee.repositories;

import org.example.recipee.domain.Ingredient;

import java.util.List;

public interface CustomIngredientRepository {
    Ingredient findIngredient(String name, String recipeeName);
    List<Ingredient> findIngredients(String recipeeName);
}
