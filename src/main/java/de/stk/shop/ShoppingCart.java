package de.stk.shop;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static de.stk.console.ColorUtil.*;
import static de.stk.console.ConsoleUtils.printEmptyLine;

/**
 * Object that holds all items the user plans to buy.
 */
public class ShoppingCart {
    /**
     * Contains all the instances of the activities the user placed inside their shopping cart.
     */
    private final ArrayList<ShoppingCartItem> shoppingCartItems = new ArrayList<>();

    /**
     * Function to print all instances of the Activities inside the cart.
     */
    public void printSummary() {
        DecimalFormat formatter = new DecimalFormat("0.00");

        float totalPriceFloat = 0;
        for (ShoppingCartItem shoppingCartItem : shoppingCartItems) {
            System.out.println(shoppingCartItem.getSummary());
            if (totalPriceFloat != 0) {
                printEmptyLine();
            }
            totalPriceFloat += shoppingCartItem.getPrice();
        }

        String totalPrice = formatter.format(totalPriceFloat);
        double totalPriceTaxFloat = totalPriceFloat * 1.19;
        String totalPriceTax = formatter.format(totalPriceTaxFloat);

        printEmptyLine();
        System.out.println(colorize("Gesamter Preis ohne Mehrwertsteuer: " + totalPrice + "€", Color.GREEN));
        System.out.println(colorize("Gesamter Preis mit Mehrwertsteuer:  " + totalPriceTax + "€", Color.BLUE));
    }

    /**
     * Function to add a specific Activity to the user's cart.
     *
     * @param cartItem The user's selected item.
     */
    //TODO: Max 3 Items!
    //TODO: Remove tickets from data as they are "reserved"
    public void addItem(ShoppingCartItem cartItem) {
        this.shoppingCartItems.add(cartItem);
    }

    /**
     * Function to buy all available Activities at once.
     */
    public void buyAllItems() {
        for (ShoppingCartItem shoppingCartItem : shoppingCartItems) {
            shoppingCartItem.buy();
        }
    }

    public void clear() {
        shoppingCartItems.clear();
    }
}
