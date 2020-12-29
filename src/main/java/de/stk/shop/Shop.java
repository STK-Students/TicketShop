package de.stk.shop;

import de.stk.data.Activity;

import java.util.ArrayList;

/**
 * This object holds all activities that can be bought.
 */
public class Shop {


    /**
     * Contains all the instances of {@link Activity}ies subclasses. These are all
     * different activities with their own values.
     */
    private ArrayList<Activity> allActivities = new ArrayList<>();

    /**
     * Contains all items which were selected by the user.
     */
    private ShoppingCart shoppingCart = new ShoppingCart();

    /**
     * Prints the summaries of all available activities.
     */
    public void printActivities() {
        for (Activity activity : allActivities) {
            System.out.println(activity.getSummary());
        }
    }

    /**
     * Gets the specified activity from the Arraylist.
     * @param index The Arraylist's index.
     * @return Gives the activity of the index.
     */
    public Activity getActivity(int index) {
        return allActivities.get(index);
    }

    /**
     * Adds an specified activity.
     * @param activity The activity with their own values.
     */
    public void addActivity(Activity activity) {
        this.allActivities.add(activity);
    }

    /**
     * Gets a ShoppingCart.
     * @return Gives the content of the ShoppingCart.
     */
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
}
