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

    private final DecimalFormat formatter = new DecimalFormat("0.00");
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

    public float getPrice() {
        return pricingType.getFactor() * boughtTickets * activity.getPricing().getPrice(priceClass);
    }

    public String getSummary() {
        String price = formatter.format(getPrice());

        String priceText;
        priceText = "Preis: " + price;
        if (pricingType != PricingType.DEFAULT) {
            priceText += "€ bei gewährtem " + pricingType.getDescription();
        }

        String activitySummary = activity.getSummary();

        String itemSummary = boughtTickets + "\n" +
                "Datum: " + day.toString() + " " +
                "Uhrzeit: " + time.toString() + "\n" +
                priceText;


        return activitySummary + itemSummary;
    }

    public void buy() {
        activity.getActivityDates().getTimeSlot(day).buyTickets(time, 5);
    }

}
