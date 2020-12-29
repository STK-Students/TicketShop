package de.stk.shop;

import java.util.ArrayList;

/**
 * Object that holds all items the user plans to buy.
 */
public class ShoppingCart {
    /**
     * Contains all the instances of the activities the user placed inside their shopping cart.
     */
    private ArrayList<ShoppingCartItem> shoppingCartItems = new ArrayList<ShoppingCartItem>();

    /**
     * Function to print all instances of the Activities inside the cart.
     */
    public void printSummary() {
        for (ShoppingCartItem shoppingCartItem : shoppingCartItems) {
            System.out.println(shoppingCartItem.getSummary());
        }
    }

    /**
     * Function to add a specific Activity to the user's cart.
     * @param cartItem The user's selected item.
     */
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

}
