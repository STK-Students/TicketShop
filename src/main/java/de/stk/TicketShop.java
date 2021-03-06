package de.stk;

import de.stk.activities.Concert;
import de.stk.activities.Exhibition;
import de.stk.activities.Reading;
import de.stk.activities.Theatre;
import de.stk.console.ConsoleDialogPrinter;
import de.stk.data.ActivityDates;
import de.stk.data.ActivityPricing;
import de.stk.shop.Shop;
import lombok.Getter;


import java.time.LocalDate;
import java.time.LocalTime;


/**
 * Main class of the TicketShop application. Creates and fills the Shop with demo activities.
 * Then creates a {@link ConsoleDialogPrinter} instance that holds all the shopping logic.
 */
public class TicketShop {

    @Getter
    private static Shop shop;

    public static void main(String[] args) {
        shop = new Shop();
        fillShop();
        new ConsoleDialogPrinter();
    }

    //ONLY DEMO METHODS THAT FILL THE SHOP AFTER THIS POINT

    private static void fillShop() {
        Theatre guttsSpektakel = new Theatre(createActivityDatesA(), new ActivityPricing(60, 40, 30), 120, "Dr. Gutt", "Das BWL Fiasko");
        Theatre trittmannsHorrorShow = new Theatre(createActivityDatesA(), new ActivityPricing(70, 40), 120, "Dr. Trittmann", "Aufnahme? Vergessen...");
        Reading slumioksWeihnachtsgedicht = new Reading(createActivityDatesA(), new ActivityPricing(12), "Das Christkind beim Finanzamt", "Dipl-Kffr. Slumiok");
        Concert demnitzAbriss = new Concert(createActivityDatesA(), new ActivityPricing(119.99F, 99.99F, 79.99F, 49.99F), 666, "Dr. DEM", "Klagen lohnt sich!");
        Exhibition patrickRZitate = new Exhibition(createSoldOutActivityDates(), new ActivityPricing(666), "Die gesammelten Zitate des Patrick R.", "Thorre & Behyad", false);

        shop.addActivity(guttsSpektakel, trittmannsHorrorShow, slumioksWeihnachtsgedicht, demnitzAbriss, patrickRZitate);
    }


    private static ActivityDates createActivityDatesA() {
        ActivityDates activityDates = new ActivityDates();

        activityDates.createTimeSlot(LocalDate.of(2021, 1, 1), LocalTime.of(16, 0), 60);
        activityDates.createTimeSlot(LocalDate.of(2021, 2, 2), LocalTime.of(16, 0), 80);
        activityDates.createTimeSlot(LocalDate.of(2021, 1, 1), LocalTime.of(20, 0), 0);
        return activityDates;
    }

    private static ActivityDates createSoldOutActivityDates() {
        ActivityDates activityDates = new ActivityDates();

        activityDates.createTimeSlot(LocalDate.of(2021, 1, 1), LocalTime.of(16, 0), 0);
        return activityDates;
    }
}
