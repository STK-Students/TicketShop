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

    public String getFormattedPriceClasses() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < prices.size(); i++) {
            String formattedPrice = formatter.format(prices.get(i));
            result.append("["+ i +"] ").append(formattedPrice).append("€ ");
        }
        return result.toString();
    }

    /**
     * Calculates the total price that has to be payed.
     *
     * @param type       A {@link PricingType} that sets the discount factor.
     * @param amount     The amount of tickets that are bought.
     * @param priceClass One of the priceClasses of this object.
     * @return The total price that has to be paid.
     */
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

        float reduction_factor;

        private float getFactor() {
            return reduction_factor;
        }

        PricingType(float reduction_factor) {
            this.reduction_factor = reduction_factor;
        }
    }
}
