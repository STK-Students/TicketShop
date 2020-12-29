package de.stk.activities;

import de.stk.data.Activity;
import de.stk.data.ActivityDates;
import de.stk.data.ActivityPricing;

public class Theatre extends Activity {

    private final int duration;
    private final String author;

    /**
     * Creates a Theatre Activity.
     *
     * @param activityDates All dates the Activity will happen at stored as an {@link ActivityDates} list.
     *                      Each date also holds information about how many tickets are available.
     * @param pricing       Different pricing classes stored as an {@link ActivityPricing} object.
     * @param duration Integer that represents the duration of the activity.
     * @param author String that represents the author of the theater play.
     */
    public Theatre(ActivityDates activityDates, ActivityPricing pricing, int duration, String author) {
        super(activityDates, pricing);
        this.duration = duration;
        this.author = author;
    }


    @Override
    public String getSummary() {
        return  "Typ: "  + getTypeName() +    "\n" +
                "Dauer: " + duration + "min" + "\n" +
                "Autor: " + author + "\n";
    }

    @Override
    public String getInformation() {
        String summary = getSummary();
        return summary +
                "Preisklassen: " + getPricing().getFormattedPriceClasses() + "\n" +
                "Termine: " + getActivityDates();

    }

    @Override
    public String setTypeName() {
        return "Theatervorstellung";
    }
}

