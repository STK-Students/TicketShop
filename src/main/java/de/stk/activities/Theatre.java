package de.stk.activities;

import de.stk.data.Activity;
import de.stk.data.ActivityDates;
import de.stk.data.ActivityPricing;

public class Theatre extends Activity {

    private final int duration;
    private final String author;
    private final String play;

    /**
     * Creates a Theatre Activity.
     * @param activityDates All dates when the Activity will happen are stored as an {@link ActivityDates} list.
     *                      Each date also holds information about how many tickets are available.
     * @param pricing       Different pricing classes stored as an {@link ActivityPricing} object.
     * @param duration      Integer that represents the duration of the activity.
     * @param author        String that represents the author of the theater play.
     * @param play          String that represents the name of the theater play.
     */
    public Theatre(ActivityDates activityDates, ActivityPricing pricing, int duration, String author, String play) {
        super(activityDates, pricing);
        this.duration = duration;
        this.author = author;
        this.play = play;
    }

    /**
     * Returns a summary of the Activity.
     * @return All values of the Activity except pricing and dates.
     */
    @Override
    public String getSummary() {
        return "Typ: " + getTypeName() + "\n" +
                "Dauer: " + duration + "min" + "\n" +
                "Autor: " + author + "\n" +
                "Theaterstück: " + play + "\n";
    }

    /**
     * Returns the prices and dates of the Activity while also calling getSummary.
     * @return The complete information of an Activity.
     */
    @Override
    public String getInformation() {
        String summary = getSummary();
        return summary +
                "Vollpreis: " + getPricing().getFormattedPriceClasses() + "\n" +
                "Termine: " + getActivityDates();

    }

    /**
     * Function to set the name of an Activity.
     * @return The name for this specific Activity.
     */
    @Override
    public String setTypeName() { return "Theatervorstellung"; }
}

