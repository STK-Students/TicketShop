package de.stk.activities;

import de.stk.console.DataFormattingUtils;
import de.stk.data.Activity;
import de.stk.data.ActivityDates;
import de.stk.data.ActivityPricing;

import java.util.ArrayList;

import static de.stk.console.ColorUtil.*;

public class Exhibition extends Activity {

    private final String topic;
    private final String exhibitor;
    private final boolean tour;

    /**
     * Creates an Exhibition Activity.
     *
     * @param activityDates All dates when the Activity will happen are stored as an {@link ActivityDates} list.
     *                      Each date also holds information about how many tickets are available.
     * @param pricing       Different pricing classes stored as an {@link ActivityPricing} object.
     * @param topic         String representing the topic of the Exhibition.
     * @param exhibitor     String representing the Name of the exhibitor.
     * @param tour          Boolean value representing if a tour is possible or not.
     */
    public Exhibition(ActivityDates activityDates, ActivityPricing pricing, String topic, String exhibitor, boolean tour) {
        super(activityDates, pricing);
        this.topic = topic;
        this.exhibitor = exhibitor;
        this.tour = tour;
    }

    /**
     * Returns a summary of the Activity.
     *
     * @return All values of the Activity except pricing and dates.
     */
    @Override
    public String getSummary() {
        String tourPossible = tour ? colorize("Ja", Color.LIGHT_GREEN) : colorize("Nein", Color.RED);

        return "Typ: " + getTypeName() + "\n" +
                "Artist: " + exhibitor + "  Tour möglich: " + tourPossible + "  Thema: ";
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
                "Vollpreis: " + pricingList + "\n" +
                "Termine: ";
    }

    @Override
    public String getUniqueName() {
        return topic;
    }

    /**
     * Function to set the name of an Activity.
     *
     * @return The name for this specific Activity.
     */
    @Override
    public String setTypeName() {
        return "Ausstellung";
    }
}
