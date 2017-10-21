package com.labelinsight.smartlabel.domain;

import java.util.List;

public class AllergenSection {

    private List<Allergen> allergens;

    public AllergenSection(List<Allergen> allergens) {
        this.allergens = allergens;
    }

    public List<Allergen> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<Allergen> allergens) {
        this.allergens = allergens;
    }

    @Override
    public String toString() {
        return "AllergenSection{" +
                "allergens=" + allergens +
                '}';
    }
}
