package de.stk.data;

import java.util.ArrayList;

public class ActivityPricing {

    /**
     * A variable amount of prices.
     * Multiple prices are used for different price classes in e.g. a theatre.
     *
     * @param prices
     */
    private final ArrayList<Float> prices = new ArrayList<>();

    public ActivityPricing(float... prices) {
        for (float price : prices) {
            this.prices.add(price);
        }
    }

    public ArrayList<Float> getPrices() {
        return prices;
    }

    public float getPrice(int index) {
        return prices.get(index);
    }

    /**
     * Determines different pricing categories that reduce the price by a certain factor.
     */
    public enum PricingType {
        DEFAULT(1F, "Kein Rabatt: ", "Normalpreis"),
        STUDENT(0.6F, "Studenten: ", "Studentenrabatt"),
        PENSIONER(0.7F, "Senioren: ", "Seniorenrabatt"),
        HANDICAPPED(0.4F, "Behinderte: ", "Behindertenrabatt");

        public float getFactor() {
            return reduction_factor;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        private float reduction_factor;
        private String name;
        private String description;

        PricingType(float reduction_factor, String name, String description) {
            this.reduction_factor = reduction_factor;
            this.name = name;
            this.description = description;
        }
    }
}
