package de.stk.activities;

import de.stk.console.DataFormattingUtils;
import de.stk.data.Activity;
import de.stk.data.ActivityDates;
import de.stk.data.ActivityPricing;

import java.util.ArrayList;

public class Theatre extends Activity {

    private final int duration;
    private final String author;
    private final String play;

    /**
     * Creates a Theatre Activity.
     *
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
     *
     * @return All values of the Activity except pricing, dates and the unique name (play).
     * These will be added in a detailed view or during the {@link de.stk.console.ConsoleDialogPrinter}.
     */
    @Override
    public String getSummary() {
        return "Typ: " + getTypeName() + "\n" +
                "Dauer: " + duration + "min" +
                "  Autor: " + author + "" +
                "  Stück: ";
    }

    /**
     * Returns the prices of the Activity while also calling getSummary.
     * Dates are left out as the only call of this method has to build an option list with those.
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

        return getSummary() + getUniqueName() + "\n" +
                "Preisklassen: " + pricingList + "\n" +
                "Termine: ";

    }

    @Override
    public String getUniqueName() {
        return play;
    }

    /**
     * Function to set the name of an Activity.
     *
     * @return The name for this specific Activity.
     */
    @Override
    public String setTypeName() {
        return "Theatervorstellung";
    }
}

