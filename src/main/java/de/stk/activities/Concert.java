package de.stk.activities;

import de.stk.console.DataFormattingUtils;
import de.stk.data.Activity;
import de.stk.data.ActivityDates;
import de.stk.data.ActivityPricing;

import java.util.ArrayList;

public class Concert extends Activity {

    private final int duration;
    private final String artist;
    private final String name;

    /**
     * Creates a Concert Activity.
     *
     * @param activityDates All dates when the Activity will happen are stored as an {@link ActivityDates} list.
     *                      Each date also holds information about how many tickets are available.
     * @param pricing       Different pricing classes stored as an {@link ActivityPricing} object.
     * @param duration      Integer representing the duration of the concert.
     * @param artist        String representing the Name of the artist(s).
     */
    public Concert(ActivityDates activityDates, ActivityPricing pricing, int duration, String artist, String name) {
        super(activityDates, pricing);
        this.duration = duration;
        this.artist = artist;
        this.name = name;
    }

    /**
     * Returns a summary of the Activity.
     *
     * @return All values of the Activity except pricing and dates.
     */
    @Override
    public String getSummary() {
        return "Typ: " + getTypeName() + "\n" +
                "Dauer: " + duration + "min" + "  Künstler: " + artist + "  Thema: ";
    }

    /**
     * Returns the prices and dates of the Activity while also calling getSummary.
     *
     * @return The complete information of an Activity.
     */
    @Override
    public String getInformation() {
        ArrayList<String> formattedPricing = DataFormattingUtils.getFormattedPricing(super.getPricing().getPrices());
        StringBuilder pricingList = new StringBuilder();
        for (String price : formattedPricing) {
            pricingList.append(price).append(" ");
        }
        String summary = getSummary();
        return summary + getUniqueName() + "\n" +
                "Preisklassen: " + pricingList + "\n" +
                "Termine: ";
    }

    @Override
    public String getUniqueName() {
        return name;
    }

    /**
     * Function to set the name of an Activity.
     *
     * @return The name for this specific Activity.
     */
    @Override
    public String setTypeName() {
        return "Konzert";
    }
}
