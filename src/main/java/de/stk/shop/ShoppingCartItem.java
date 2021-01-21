package de.stk.shop;

import de.stk.data.Activity;
import de.stk.data.ActivityPricing.PricingType;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The {@link ShoppingCartItem} is a temporary object that is used during the shopping process.
 * It holds one object extending {@link Activity} - that's the actual activity the ticket is bought for.
 * Furthermore, the {@link ShoppingCartItem} holds information about how many tickets the buyer bought
 * for the hold {@link Activity}. The price per ticket is also stored.
 */
public class ShoppingCartItem {

    @Getter
    private final Activity activity;

    private final DecimalFormat formatter = new DecimalFormat("0.00");
    /*
    Unique Shopping Cart values. These are all chosen from the list of possible options.
     */
    @Getter @Setter
    private LocalDate day;
    @Getter @Setter
    private LocalTime time;
    @Setter @Getter
    private int boughtTickets;
    @Setter @Getter
    private int priceClass;
    @Setter
    private PricingType pricingType;

    public ShoppingCartItem(Activity activity) {
        this.activity = activity;
    }

    public void buy() {
        activity.getActivityDates().getTimeSlot(day).buyTickets(time, boughtTickets);
    }

    public void refund() {
        activity.getActivityDates().getTimeSlot(day).refundTickets(time, boughtTickets);
    }

    public float getPrice() {
        return pricingType.getReductionFactor() * boughtTickets * activity.getPricing().getPrice(priceClass);
    }

    /**
     * Ensures that this Item is complete and ready for billing.
     * @return a boolean; true if complete, false if incomplete
     */
    public boolean isComplete() {
        return day != null && time != null && boughtTickets != 0 && pricingType != null;
    }

    public String getSummary() {
        String price = formatter.format(getPrice());

        String priceText;
        priceText = "Preis: " + price;
        if (pricingType != PricingType.DEFAULT) {
            priceText += "€ bei gewährtem " + pricingType.getDescription();
        }

        String activitySummary = activity.getSummary() + activity.getUniqueName();

        String itemSummary = "\n" +
                "Anzahl Karten: " + boughtTickets + " " +
                "Datum: " + day.toString() + " " +
                "Uhrzeit: " + time.toString() + "\n" +
                priceText;


        return activitySummary + itemSummary;
    }

}
