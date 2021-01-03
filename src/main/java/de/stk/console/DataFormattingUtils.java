package de.stk.console;

import de.stk.data.ActivityDates;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
     * Formats the data of an {@link ActivityDates} object into human readable strings and returns each date alongside
     * it's {@link LocalDate} and {@link LocalTime} objects.
     *
     * @param dates The {@link ActivityDates} object that's formatted data will be returned.
     * @return A HashMap<String, Pair<LocalDate, LocalTime>> each string is one date and has a pair of
     * LocalDate and LocalTime objects assigned to it. The string will be strikedthrough and the pair will be null
     * if all tickets for a specific date are sold out.
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
}
