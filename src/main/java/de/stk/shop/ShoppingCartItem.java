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
    private int boughtTickets;
    private PricingType pricingType;
    private int priceClass;
    private LocalDate day;
    private LocalTime time;

    public ShoppingCartItem(Activity activity) {
        this.activity = activity;
    }

    public void setBoughtTickets(int boughtTickets) {
        this.boughtTickets = boughtTickets;
    }

    public void setPricingType(PricingType pricingType) {
        this.pricingType = pricingType;
    }

    public void setPriceClass(int priceClass) {
        this.priceClass = priceClass;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Activity getActivity() {
        return activity;
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
