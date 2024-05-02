package org.example.recipee.vo;

import java.util.ArrayList;
import java.util.List;

public class RecipeeVO {

    private String description;

    private String cookingInstructions;

    private List<IngredientRecipeeVO> ingredients = new ArrayList<>();

    public String getCookingInstructions() {
        return cookingInstructions;
    }

    public void setCookingInstructions(String cookingInstructions) {
        this.cookingInstructions = cookingInstructions;
    }

    public List<IngredientRecipeeVO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientRecipeeVO> ingredients) {
        this.ingredients = ingredients;
    }
}
