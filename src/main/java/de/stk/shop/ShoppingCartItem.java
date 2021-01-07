package de.stk.shop;

import de.stk.data.Activity;
import de.stk.data.ActivityPricing.PricingType;

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

    public int getTickets() {
        return boughtTickets;
    }

    public PricingType getPricingType() {
        return pricingType;
    }

    public int getPriceClass() {
        return priceClass;
    }

    public LocalDate getDay() {
        return day;
    }

    public LocalTime getTime() {
        return time;
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
        DecimalFormat formatter = new DecimalFormat("0.00");
        float totalPrice = pricingType.getFactor() * boughtTickets * activity.getPricing().getPrice(priceClass);
        String formattedPrice = formatter.format(totalPrice);

        String activitySummary = activity.getSummary();

        String itemSummary = boughtTickets + "\n" +
                "Datum: " + day.toString()  + " "+
                "Uhrzeit: " + time.toString() + "\n" +
                "Gesamter Preis: " + formattedPrice + "€";

        return activitySummary + itemSummary;
    }

    public void buy() {
        activity.getActivityDates().getTimeSlot(day).buyTickets(time, 5);
    }

}
