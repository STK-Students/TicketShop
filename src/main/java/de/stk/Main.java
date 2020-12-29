package de.stk;

import de.stk.activities.Theatre;
import de.stk.data.Activity;
import de.stk.data.ActivityDates;
import de.stk.data.ActivityPricing;
import de.stk.shop.Shop;
import de.stk.shop.ShoppingCartItem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {

    private static Shop shop;

    public static void main(String[] args) {
        shop = new Shop();
        fillShop();
        Scanner scanner = new Scanner(System.in);

        System.out.println("------------------------------------");
        System.out.println("Alle Angebote unseres Shops:");
        shop.printActivities();
        String selectedProduct = scanner.next();
        System.out.println("------------------------------------");
        System.out.println("Detailansicht:");
        int index = Integer.parseInt(selectedProduct);
        Activity currentActivity = shop.getActivity(index);
        currentActivity.getSummary();


        System.out.println("------------------------------------");
        System.out.println("------------------------------------");
        System.out.println("Diese Produkte befinden sich in ihrem Warenkorb:");
        shop.getShoppingCart().printSummary();
        shop.getShoppingCart().buyAllItems();
        System.out.println("------------------------------------");
        System.out.println("Ihre Rechnung:");
        shop.getShoppingCart().buyAllItems();

    }


    private static void fillShop() {
        Theatre guttsSpektakel = createTheaterExample();
        shop.addActivity(guttsSpektakel);

        ShoppingCartItem example = createShoppingCartItem(guttsSpektakel);
        shop.getShoppingCart().addItem(example);
    }

    private static Theatre createTheaterExample() {
        ActivityDates activityDates = new ActivityDates();

        activityDates.createTimeSlot(LocalDate.of(2021, 1, 1), LocalTime.of(20, 0), 60);
        activityDates.createTimeSlot(LocalDate.of(2021, 2, 2), LocalTime.of(16, 0), 80);
        activityDates.createTimeSlot(LocalDate.of(2021, 1, 1), LocalTime.of(20, 0), 60);

        //Mehrere Preisklassen anlegen
        ActivityPricing activityPricing = new ActivityPricing(60, 40, 30);
        return new Theatre(activityDates, activityPricing, 120, "Dr. Gutt");
    }

    private static ShoppingCartItem createShoppingCartItem(Activity activity) {
        return new ShoppingCartItem(activity,3, ActivityPricing.PricingType.STUDENT, 0, LocalDate.of(2021,1,1), LocalTime.of(20, 0));
    }
}
