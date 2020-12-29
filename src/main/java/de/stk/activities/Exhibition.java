package de.stk.activities;

import de.stk.data.Activity;
import de.stk.data.ActivityDates;
import de.stk.data.ActivityPricing;

public class Exhibition extends Activity {

    private String topic;
    private String artist;
    private boolean tour;

    /**
     * Creates an Exhibition activity.
     *
     * @param activityDates All dates the activity will happen at and
     *                      the amount of tickets available per date stored as an {@link ActivityDates} list.
     * @param pricing       Different pricing classes stored as an {@link ActivityPricing} object.
     */
    public Exhibition(ActivityDates activityDates, ActivityPricing pricing, String typeName, String topic, String artist, boolean tour) {
        super(activityDates, pricing);
        this.topic = topic;
        this.artist = artist;
        this.tour = tour;
    }

    @Override
    public String getSummary() {
        String tourPossible = tour ? "Ja." : "Nein.";

        return "Topic: " + topic + "\n" +
                "Artist: " + artist + "\n" +
                "Tour möglich: " + tourPossible;
    }

    @Override
    public String getInformation() {
        return null;
    }

    @Override
    public String setTypeName() {
        return "Ausstellung";
    }
}
