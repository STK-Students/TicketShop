package de.stk.data;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

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
    @Getter
    private final HashMap<LocalDate, DailySchedule> activityDates = new HashMap<>();

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

    public static class DailySchedule {

        @Getter
        HashMap<LocalTime, Integer> timeSlots = new HashMap<>();

        public void addScheduleEntry(LocalTime timeSlot, Integer availableTickets) {
            timeSlots.put(timeSlot, availableTickets);
        }

        public int getAvailableTickets(LocalTime time) {
            return timeSlots.get(time);
        }

        public void buyTickets(LocalTime timeSlot, int boughtTickets) {
            timeSlots.put(timeSlot, timeSlots.get(timeSlot) - boughtTickets);
        }

        public void refundTickets(LocalTime timeSlot, int refundedTickets) {
            timeSlots.put(timeSlot, timeSlots.get(timeSlot) + refundedTickets);
        }
    }
}
