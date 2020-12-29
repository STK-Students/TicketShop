package de.stk.activities;

import de.stk.data.Activity;
import de.stk.data.ActivityDates;
import de.stk.data.ActivityPricing;

public class Reading extends Activity {

    private String topic;
    private String artist;
    private boolean tour;

    public Reading(ActivityDates activityDates,String typeName, ActivityPricing pricing) {
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
        return "Lesung";
    }
}
