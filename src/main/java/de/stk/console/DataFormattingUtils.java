package de.stk.console;

import de.stk.data.ActivityDates;
import de.stk.data.ActivityPricing;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static de.stk.data.ActivityPricing.PricingType;

public class DataFormattingUtils {

    private static final DecimalFormat formatter = new DecimalFormat("0.00");

    //TODO: Formatted Pricing does not include any options.
    public static ArrayList<String> getFormattedPricing(ArrayList<Float> prices) {
        ArrayList<String> resultArray = new ArrayList<>();

        for (Float price : prices) {
            String formattedPrice = formatter.format(price);
            resultArray.add(formattedPrice + "€");
        }
        return resultArray;
    }

    /**
     * Formats the data of an {@link ActivityDates} object into a HashMap that consists of a
     * human readable string (Date, Time, Amount of tickets left) and a pair of {@link LocalDate}
     * and {@link LocalTime} objects. Dates that have no tickets left will be crossed out.
     *
     * @param dates The {@link ActivityDates} object that's formatted data will be returned.
     * @return A HashMap<String, Pair<LocalDate, LocalTime>> each string holds date, time and the amount of available
     * tickets. Each string has a pair of LocalDate and LocalTime objects assigned to it.
     * The string will be crossed out and the pair will be null if all tickets for a specific date are sold out.
     */
    public static HashMap<String, Pair<LocalDate, LocalTime>> getFormattedDates(ActivityDates dates) {
        HashMap<LocalDate, ActivityDates.DailySchedule> activityDates = dates.getActivityDates();
        HashMap<String, Pair<LocalDate, LocalTime>> resultArray = new HashMap<>();

        for (Map.Entry<LocalDate, ActivityDates.DailySchedule> dateEntry : activityDates.entrySet()) {
            String date = dateEntry.getKey().toString();

            HashMap<LocalTime, Integer> dailySchedule = dateEntry.getValue().getTimeSlots();
            for (Map.Entry<LocalTime, Integer> daySchedule : dailySchedule.entrySet()) {
                StringBuilder result = new StringBuilder();

                String time = daySchedule.getKey().toString();
                Integer leftOverTickets = daySchedule.getValue();

                result.append(date).append(": ").append(time);
                result.append(" Übrige Tickets: ").append(leftOverTickets);

                if (leftOverTickets == 0) {
                    String strikedThroughDate = ColorUtil.strikeThrough(result.toString());
                    resultArray.put(strikedThroughDate, null);
                } else {
                    LocalDate currentDate = dateEntry.getKey();
                    LocalTime currentTime = daySchedule.getKey();
                    resultArray.put(result.toString(), new ImmutablePair<>(currentDate, currentTime));
                }
            }
        }
        return resultArray;
    }

    /**
     * Calculates the price respecting amount and price class for all {@link PricingType}s.
     *
     * @param activityPricing An instance of {@link ActivityPricing}.
     * @param amount          The amount of tickets that are bought.
     * @param priceClass      One of the priceClasses of this object.
     * @return The total price that has to be paid.
     */
    public static ArrayList<String> calcAllPrices(ActivityPricing activityPricing, int amount, int priceClass) {
        ArrayList<String> result = new ArrayList<>();

        for (PricingType type : PricingType.values()) {
            float price = type.getReductionFactor() * amount * activityPricing.getPrice(priceClass);
            String formattedPrice = formatter.format(price);

            result.add("Gesamter Preis " + type.getName() + formattedPrice + "€");
        }
        return result;
    }
}
