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

public class Main {

    @Getter
    private static Shop shop;
    public static void main(String[] args) {
        shop = new Shop();
        fillShop();
        new ConsoleDialogPrinter();
    }

    //ONLY DEMO METHODS THAT FILL THE SHOP AFTER THIS POINT

    private static void fillShop() {
        Theatre guttsSpektakel = new Theatre(createActivityDates(), new ActivityPricing(60, 40, 30), 120,"Dr. Gutt", "Das BWL Fiasko");
        Theatre trittmannsHorrorShow = new Theatre(createActivityDates(), new ActivityPricing(70, 40), 120, "Dr. Trittmann", "Aufnahme? Vergessen...");
        Reading slumioksWeihnachtsgedicht = new Reading(createActivityDates(),new ActivityPricing(12), "Das Christkind beim Finanzamt", "Dipl-Kffr. Slumiok");
        Concert demnitzAbriss = new Concert(createActivityDates(),new ActivityPricing(119.99F,99.99F,79.99F,49.99F),666,"Dr. DEM","Dr. Dems Eskalation");
        Exhibition patrickRZitate = new Exhibition(createActivityDates(), new ActivityPricing(666), "Die gesammelten Zitate des Patrick R.", "Thorre & Beyhad", false);

        shop.addActivity(guttsSpektakel, trittmannsHorrorShow, slumioksWeihnachtsgedicht, demnitzAbriss, patrickRZitate);
    }


    private static ActivityDates createActivityDates() {
        ActivityDates activityDates = new ActivityDates();

        activityDates.createTimeSlot(LocalDate.of(2021, 1, 1), LocalTime.of(16, 0), 60);
        activityDates.createTimeSlot(LocalDate.of(2021, 2, 2), LocalTime.of(16, 0), 80);
        activityDates.createTimeSlot(LocalDate.of(2021, 1, 1), LocalTime.of(20, 0), 0);
        return activityDates;
    }
}
