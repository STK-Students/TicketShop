package de.stk.data;

import lombok.Getter;

import java.util.ArrayList;

/**
 * Holds all pricing information of an activity.
 * This includes price classes and discounts.
 */
public class ActivityPricing {

    /**
     * A variable amount of prices.
     * Multiple prices are used for different price classes in e.g. a theatre.
     *
     * @param prices
     */
    @Getter
    private final ArrayList<Float> prices = new ArrayList<>();

    public ActivityPricing(float... prices) {
        for (float price : prices) {
            this.prices.add(price);
        }
    }

    public float getPrice(int index) {
        return prices.get(index);
    }

    /**
     * Determines different pricing categories that reduce the price by a certain factor.
     */
    public enum PricingType {
        DEFAULT(1F, "Kein Rabatt: ", "Normalpreis."),
        STUDENT(0.6F, "Studenten: ", "Studentenrabatt von 40%."),
        PENSIONER(0.7F, "Senioren: ", "Seniorenrabatt von 30%."),
        HANDICAPPED(0.4F, "Behinderte: ", "Behindertenrabatt von 60%.");


        @Getter
        private final float reductionFactor;
        @Getter
        private final String name;
        @Getter
        private final String description;

        PricingType(float reductionFactor, String name, String description) {
            this.reductionFactor = reductionFactor;
            this.name = name;
            this.description = description;
        }
    }
}
