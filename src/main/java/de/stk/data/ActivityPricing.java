package de.stk.data;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ActivityPricing {

    /**
     * A variable amount of prices.
     * Multiple prices are used for different price classes in e.g. a theatre.
     *
     * @param prices
     */
    private final ArrayList<Float> prices = new ArrayList<>();

    private final DecimalFormat formatter = new DecimalFormat("0.00");

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
     * Calculates the total price that has to be payed.
     *
     * @param type       A {@link PricingType} that sets the discount factor.
     * @param amount     The amount of tickets that are bought.
     * @param priceClass One of the priceClasses of this object.
     * @return The total price that has to be paid.
     */
    //TODO: Move this somewhere else. This should not be part of a data class.
    public String calcPrice(PricingType type, int amount, int priceClass) {
        float result = type.getFactor() * amount * prices.get(priceClass);
        return formatter.format(result);
    }

    /**
     * Determines different pricing categories that reduce the price by a certain factor.
     */
    public enum PricingType {
        DEFAULT(1F),
        STUDENT(0.6F),
        PENSIONER(0.7F),
        HANDICAPPED(0.4F);

        public float getFactor() {
            return reduction_factor;
        }

        public float reduction_factor;

        PricingType(float reduction_factor) {
            this.reduction_factor = reduction_factor;
        }
    }
}
