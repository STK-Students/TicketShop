package de.stk;

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
        Theatre guttsSpektakel = createTheaterExample("Dr. Gutt", "Das BWL Fiasko");
        Theatre trittmannsHorrorShow = createTheaterExample("Dr. Trittmann", "Aufnahme? Vergessen...");

        shop.addActivity(guttsSpektakel, trittmannsHorrorShow);
    }

    private static Theatre createTheaterExample(String author, String name) {
        ActivityDates activityDates = new ActivityDates();

        activityDates.createTimeSlot(LocalDate.of(2021, 1, 1), LocalTime.of(16, 0), 60);
        activityDates.createTimeSlot(LocalDate.of(2021, 2, 2), LocalTime.of(16, 0), 80);
        activityDates.createTimeSlot(LocalDate.of(2021, 1, 1), LocalTime.of(20, 0), 0);

        ActivityPricing activityPricing = new ActivityPricing(60, 40, 30);
        return new Theatre(activityDates, activityPricing, 120, author, name);
    }

//    private static ShoppingCartItem createShoppingCartItem(Activity activity) {
//        return new ShoppingCartItem(activity, 3, ActivityPricing.PricingType.STUDENT, 0, LocalDate.of(2021, 1, 1), LocalTime.of(20, 0));
//    }
}
