package de.stk.shop;

import de.stk.data.Activity;
import de.stk.data.ActivityPricing.PricingType;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The {@link ShoppingCartItem} is a temporary object that is used during the shopping process.
 * It holds one object extending {@link Activity} - that's the actual activity the ticket is bought for.
 * Furthermore, the {@link ShoppingCartItem} holds information about how many tickets the buyer bought
 * for the hold {@link Activity}. The price per ticket is also stored.
 */
public class ShoppingCartItem {

    private final Activity activity;

    /*
    Unique Shopping Cart values. These are all chosen from the list of possible options.
     */
    private final int boughtTickets;
    private final PricingType pricingType;
    private final int priceClass;
    private final LocalDate day;
    private final LocalTime time;

    public ShoppingCartItem(Activity activity, int boughtTickets, PricingType pricingType, int priceClass, LocalDate day, LocalTime time) {
        this.activity = activity;
        this.pricingType = pricingType;
        this.boughtTickets = boughtTickets;
        this.priceClass = priceClass;
        this.day = day;
        this.time = time;

    }

    public String getSummary() {
        String totalPrice = activity.getPricing().calcPrice(pricingType, boughtTickets, priceClass);

        String activitySummary = activity.getSummary();

        String itemSummary = "Datum: " + day.toString() + "\n" +
                "Uhrzeit: " + time.toString() + "\n" +
                "Gesamter Preis: " + totalPrice + "€";

        return activitySummary + itemSummary;
    }

    public void buy() {
        activity.getActivityDates().getTimeSlot(day).buyTickets(time, 5);
    }

    public String getPrice() {
        return activity.getPricing().calcPrice(pricingType, boughtTickets, priceClass);
    }


}
