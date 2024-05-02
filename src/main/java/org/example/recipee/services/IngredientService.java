package org.example.recipee.services;

import org.example.recipee.domain.Ingredient;
import org.example.recipee.domain.IngredientId;
import org.example.recipee.domain.Recipee;
import org.example.recipee.repositories.IngredientRepository;
import org.example.recipee.repositories.RecipeeRepository;
import org.example.recipee.vo.IngredientRecipeeVO;
import org.example.recipee.vo.QuantityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeeRepository recipeeRepository;

    public QuantityVO findByName(String recipeeName, String name) {
        Ingredient ingredient = ingredientRepository.findIngredient(name, recipeeName);

        QuantityVO quantityVO = new QuantityVO();
        quantityVO.setQuantity(ingredient.getQuantity());
        return quantityVO;
    }

    public List<IngredientRecipeeVO> findIngredients(String recipeeName) {
        return ingredientRepository.findIngredients(recipeeName)
                .stream().map(ing -> {
                    IngredientRecipeeVO ingredientRecipeeVO = new IngredientRecipeeVO();
                    ingredientRecipeeVO.setQuantity(ing.getQuantity());
                    ingredientRecipeeVO.setName(ing.getName());
                    return ingredientRecipeeVO;
                }).sorted(Comparator.comparing(IngredientRecipeeVO::getName)).toList();
    }

    public void updateIngredient(QuantityVO quantityVO,String recipeeName, String name){
        Ingredient ingredient = this.ingredientRepository.findIngredient(name, recipeeName);
        ingredient.setQuantity(quantityVO.getQuantity());
        ingredientRepository.save(ingredient);
    }

    public void addIngredient(IngredientRecipeeVO ingredientRecipeeVO, String recipeeName){
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientRecipeeVO.getName());
        ingredient.setQuantity(ingredientRecipeeVO.getQuantity());

        Recipee recipee = recipeeRepository.findById(recipeeName).orElseThrow(() -> new NullPointerException("No recipee found"));
        ingredient.setRecipee(recipee);
        recipee.getIngredients().add(ingredient);
        ingredientRepository.save(ingredient);
    }

    public void delete(String recipeeName, String name) {
        IngredientId ingredientId = new IngredientId();
        ingredientId.setName(name);
        ingredientId.setRecipee(recipeeName);
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElse(null);
        Recipee recipee = ingredient.getRecipee();
        recipee.getIngredients().remove(ingredient);
        recipeeRepository.save(recipee);
    }
}
