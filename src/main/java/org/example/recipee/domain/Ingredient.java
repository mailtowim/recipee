package org.example.recipee.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
@IdClass(IngredientId.class)
public class Ingredient {

    @Id
    private String name;

    private String quantity;

    @Id
    @ManyToOne
    private Recipee recipee;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Recipee getRecipee() {
        return recipee;
    }

    public void setRecipee(Recipee recipee) {
        this.recipee = recipee;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient that)) return false;

        return Objects.equals(name, that.name) && Objects.equals(recipee, that.recipee);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(recipee);
        return result;
    }
}
