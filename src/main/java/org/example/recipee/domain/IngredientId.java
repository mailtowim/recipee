package org.example.recipee.domain;

import java.util.Objects;

public class IngredientId {

    private String name;

    private String recipee;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecipee() {
        return recipee;
    }

    public void setRecipee(String recipee) {
        this.recipee = recipee;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IngredientId that)) return false;

        return Objects.equals(name, that.name) && Objects.equals(recipee, that.recipee);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(recipee);
        return result;
    }
}
