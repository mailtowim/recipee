package org.example.recipee.repositories;

import org.example.recipee.domain.Ingredient;
import org.example.recipee.domain.IngredientId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, IngredientId>, CustomIngredientRepository {
}
