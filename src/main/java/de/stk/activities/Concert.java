package de.stk.activities;

import de.stk.data.Activity;
import de.stk.data.ActivityDates;
import de.stk.data.ActivityPricing;

public class Concert extends Activity {

    private int duration;
    private String artist;

    public Concert(ActivityDates activityDates, String typeName, ActivityPricing pricing) {
        super(activityDates, pricing);
    }

    @Override
    public String getSummary() {
        return null;
    }

    @Override
    public String getInformation() {
        return null;
    }

    @Override
    public String setTypeName() {
        return "Konzert";
    }
}
