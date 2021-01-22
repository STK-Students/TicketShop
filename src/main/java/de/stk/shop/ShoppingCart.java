package de.stk.shop;

import de.stk.console.ConsoleUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static de.stk.console.ColorUtil.Color;
import static de.stk.console.ColorUtil.colorize;
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
        printEmptyLine();

        DecimalFormat formatter = new DecimalFormat("0.00");

        float totalPriceFloat = 0;
        for (ShoppingCartItem shoppingCartItem : shoppingCartItems) {
            totalPriceFloat += shoppingCartItem.getPrice();

            System.out.println(shoppingCartItem.getSummary());
            printEmptyLine();
        }

        String totalPrice = formatter.format(totalPriceFloat);
        double totalPriceTaxFloat = totalPriceFloat * 1.19;
        String totalPriceTax = formatter.format(totalPriceTaxFloat);

        printEmptyLine();
        System.out.println(colorize("Gesamter Preis exkl. 19% Mehrwertsteuer: " + totalPrice + ConsoleUtils.EURO, Color.GREEN));
        System.out.println(colorize("Gesamter Preis inkl. 19% Mehrwertsteuer: " + totalPriceTax + ConsoleUtils.EURO, Color.LIGHT_BLUE));
    }


    /**
     * Adds a specific Activity to the user's cart.
     * Validates
     *
     * @param cartItem The user's selected item.
     * @return boolean whether or not the limit of 3 different activities is reached.
     */

    public boolean addItem(ShoppingCartItem cartItem) {
        if (shoppingCartItems.size() == 3) {
            return false;
        }

        cartItem.buy();
        shoppingCartItems.add(cartItem);

        return true;
    }

    public void clear() {
        for (ShoppingCartItem cartItem : shoppingCartItems) {
            cartItem.refund();
        }
        shoppingCartItems.clear();
    }

    /**
     * Check whether the shoppingCart holds item.
     * @return true if the ShoppingCart holds items.
     */
    public boolean holdsItems() {
        return shoppingCartItems.size() >= 1;
    }
}
