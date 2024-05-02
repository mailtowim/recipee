package org.example.recipee.repositories;

import org.example.recipee.domain.Ingredient;
import org.example.recipee.domain.Recipee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
public class IngredientRepositoryTest {

    // Alternative for EntityManager
    // Optional in this case, we can use bookRepository to do the same stuff
    @Autowired
    private TestEntityManager testEM;
    @Autowired
    private RecipeeRepository recipeeRepository;

    @Test
    public void testSave() {

        Recipee recipee = new Recipee();
        recipee.setName("Miso soup");
        recipee.setDescription("Japanse miso soup on Authentic Japanese cooking");
        recipee.setCookingInstructions("Heat the soup");
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Katsuobushi");
        ingredient.setQuantity("4 gram");
        ingredient.setRecipee(recipee);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Hoorn des overvloeds");
        ingredient2.setQuantity("4 gram");
        ingredient2.setRecipee(recipee);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient2);
        ingredients.add(ingredient);
        recipee.setIngredients(ingredients);

        recipeeRepository.save(recipee);


        Recipee result = recipeeRepository.findById("Miso soup").orElseThrow();
        assertNotNull(result);

    }
}
