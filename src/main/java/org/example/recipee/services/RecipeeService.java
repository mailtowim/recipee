package org.example.recipee.services;

import org.example.recipee.domain.Ingredient;
import org.example.recipee.domain.Recipee;
import org.example.recipee.repositories.RecipeeRepository;
import org.example.recipee.vo.IngredientRecipeeVO;
import org.example.recipee.vo.RecipeeVO;
import org.example.recipee.vo.RecipeeWithNameVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RecipeeService {

    @Autowired
    private RecipeeRepository recipeeRepository;

    public List<RecipeeWithNameVO> getRecipees() {
        return recipeeRepository.findAll().stream().sorted(Comparator.comparing(Recipee::getName))
                .map(rc -> {
                    RecipeeWithNameVO result = new RecipeeWithNameVO();
                    result.setName(rc.getName());
                    result.setIngredients(rc.getIngredients().stream().map(ing -> {
                        IngredientRecipeeVO ingredientRecipeeVO = new IngredientRecipeeVO();
                        ingredientRecipeeVO.setName(ing.getName());
                        ingredientRecipeeVO.setQuantity(ing.getQuantity());
                        return ingredientRecipeeVO;
                    }).sorted(Comparator.comparing(IngredientRecipeeVO::getName)).toList());
                    result.setCookingInstructions(rc.getCookingInstructions());
                    return result;
                }).toList();
    }

    public RecipeeVO findByName(String name) {
        return recipeeRepository.findById(name).map(rc -> {
            RecipeeVO recipeeVO = new RecipeeVO();
            recipeeVO.setIngredients(rc.getIngredients().stream().map(ing -> {
                IngredientRecipeeVO ingredientRecipeeVO = new IngredientRecipeeVO();
                ingredientRecipeeVO.setName(ing.getName());
                ingredientRecipeeVO.setQuantity(ing.getQuantity());
                return ingredientRecipeeVO;
            }).sorted(Comparator.comparing(IngredientRecipeeVO::getName)).toList());
            return recipeeVO;
        }).orElse(null);
    }

    public void delete(String name) {
        recipeeRepository.deleteById(name);
    }

    public void updateRecipee(String name, RecipeeVO recipeeVO) {
        Recipee recipee = recipeeRepository.findById(name).orElseThrow(() -> new NullPointerException("recipee was not found"));
        recipee.setDescription(recipeeVO.getCookingInstructions());
        recipee.setCookingInstructions(recipeeVO.getCookingInstructions());

        Map<String, Ingredient> ingredientMap = recipee.getIngredients().stream().collect(Collectors.toMap(Ingredient::getName, Function.identity()));

        recipeeVO.getIngredients().stream().forEach(ing -> {
            if (ingredientMap.containsKey(ing.getName())) {
                Ingredient ingredient = ingredientMap.get(ing.getName());
                ingredient.setQuantity(ing.getQuantity());
            } else {
                Ingredient ingredient = new Ingredient();
                ingredient.setQuantity(ing.getQuantity());
                ingredient.setName(ing.getName());
                ingredient.setRecipee(recipee);
                recipee.getIngredients().add(ingredient);
            }
        });

        Set<String> deleted = new TreeSet<>(ingredientMap.keySet());
        Set<String> newIngredients = recipeeVO.getIngredients().stream().map(IngredientRecipeeVO::getName).collect(Collectors.toSet());
        deleted.removeAll(newIngredients);


        for (String s: deleted){
            recipee.getIngredients().remove(ingredientMap.get(s));
        }

        recipeeRepository.save(recipee);
    }

    public void add(RecipeeWithNameVO recipeeVO) {
        Recipee recipee = new Recipee();
        recipee.setName(recipeeVO.getName());
        recipee.setDescription(recipee.getDescription());
        recipee.setCookingInstructions(recipeeVO.getCookingInstructions());
        recipee.setIngredients(recipeeVO.getIngredients().stream()
                .map(ing -> {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(ing.getName());
                    ingredient.setRecipee(recipee);
                    ingredient.setQuantity(ing.getQuantity());
                    return ingredient;
                }).toList());

        recipeeRepository.save(recipee);
    }
}
