package org.example.recipee.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.recipee.domain.Ingredient;

import java.util.List;

public class CustomIngredientRepositoryImpl implements CustomIngredientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Ingredient findIngredient(String name, String recipeeName) {
        return entityManager.createQuery("FROM Ingredient i WHERE i.name = :name and i.recipee.name = :recipeeName", Ingredient.class)
                .setParameter("name", name)
                .setParameter("recipeeName", recipeeName)
                .getSingleResult();
    }

    @Override
    public List<Ingredient> findIngredients(String recipeeName) {
        return entityManager.createQuery("FROM Ingredient i WHERE i.recipee.name = :recipeeName", Ingredient.class)
                .setParameter("recipeeName", recipeeName)
                .getResultList();
    }
}
