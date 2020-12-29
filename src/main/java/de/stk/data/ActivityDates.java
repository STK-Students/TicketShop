package de.stk.data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds the information about when the Activity will take place.
 * An Activity can happen multiple times, even on the same day.
 * Each instance of an Activity has it's own amount of available tickets.
 * Example:
 * 1.1.2021: 10:00 o'clock 20 Tickets
 * 1.1.2020: 15:00 o'clock 20 Tickets
 */

public class ActivityDates {

    /**
     * This HashMap contains all dates the Activity will take place at.
     * Each date has one instance of the {@link DailySchedule} class assigned to it.
     * The {@link DailySchedule} class contains multiple time slots since e.g. a concert could be played
     * twice a day.
     * Each time slot then has an Integer that represents the amount of Tickets
     * that are left.
     */
    private final HashMap<LocalDate, DailySchedule> activityDates = new HashMap<>();

    public ActivityDates() {
    }

    public String getFormattedDates() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<LocalDate, DailySchedule> entry : activityDates.entrySet()) {
            String date = entry.getKey().toString();

            HashMap<LocalTime, Integer> dailySchedule = entry.getValue().timeSlots;
            for (Map.Entry<LocalTime, Integer> daySchedule : dailySchedule.entrySet()) {
                if (daySchedule.getValue() == 0) {

                }

                //2020-01-01: 10:00
                result.append(date).append(": ").append(daySchedule.getKey().toString());
                //2020-01-01: 10:00 Übrige Tickets: 0
                result.append(" Übrige Tickets: ").append(daySchedule.getValue());
            }
        }
        return "";
    }

    /**
     * @param date             The day that the timeslot will be added to.
     * @param time             The unique time the activity will start at.
     * @param availableTickets The amount of available tickets for this timeslot.
     */
    public void createTimeSlot(LocalDate date, LocalTime time, int availableTickets) {
        DailySchedule dailySchedule;

        if (activityDates.get(date) == null) {
            dailySchedule = new DailySchedule();
        } else {
            dailySchedule = activityDates.get(date);
        }

        dailySchedule.addScheduleEntry(time, availableTickets);
        activityDates.put(date, dailySchedule);
    }

    public DailySchedule getTimeSlot(LocalDate data) {
        return activityDates.get(data);
    }

    public class DailySchedule {

        HashMap<LocalTime, Integer> timeSlots = new HashMap<>();

        public DailySchedule() {
        }

        public void addScheduleEntry(LocalTime timeSlot, Integer availableTickets) {
            timeSlots.put(timeSlot, availableTickets);
        }

        public void buyTickets(LocalTime timeSlot, int boughtTickets) {
            timeSlots.put(timeSlot, timeSlots.get(timeSlot) - boughtTickets);

        }
    }
}
