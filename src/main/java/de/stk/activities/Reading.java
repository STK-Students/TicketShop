package de.stk.activities;

import de.stk.console.DataFormattingUtils;
import de.stk.data.Activity;
import de.stk.data.ActivityDates;
import de.stk.data.ActivityPricing;

public class Reading extends Activity {

    private final String topic;
    private final String artist;


    /**
     * Creates a Reading Activity.
     * @param activityDates All dates when the Activity will happen are stored as an {@link ActivityDates} list.
     *                      Each date also holds information about how many tickets are available.
     * @param pricing       Different pricing classes stored as an {@link ActivityPricing} object.
     * @param topic         String that represents the topic of the Reading.
     * @param artist        String that represents the name of the person reading out.
     */
    public Reading(ActivityDates activityDates, ActivityPricing pricing, String topic, String artist) {
        super(activityDates, pricing);
        this.topic = topic;
        this.artist = artist;
    }

    /**
     * Returns a summary of the Activity.
     * @return All values of the Activity except pricing and dates.
     */
    @Override
    public String getSummary() {
        return "Typ: " + getTypeName() + "\n" +
                "Thema: " + topic  + "\n" +
                "Leser: " + artist + "\n";
    }

    /**
     * Returns the prices and dates of the Activity while also calling getSummary.
     * @return The complete information of an Activity.
     */
    @Override
    public String getInformation() {
        return getSummary() + getUniqueName() +
                "Vollpreis: " + DataFormattingUtils.getFormattedPricing(super.getPricing().getPrices()) + "\n" +
                "Termine: " + getActivityDates();
    }

    @Override
    public String getUniqueName() {
        return topic;
    }

    /**
     * Function to set the name of an Activity.
     * @return The name for this specific Activity.
     */
    @Override
    public String setTypeName() { return "Lesung"; }
}
