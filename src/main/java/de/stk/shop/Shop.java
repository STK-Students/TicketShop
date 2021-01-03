package de.stk.shop;

import de.stk.data.Activity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This object holds all activities that can be bought.
 */
public class Shop {


    /**
     * Contains all the instances of {@link Activity}ies subclasses. These are all
     * different activities with their own values.
     */
    //TODO: Check if this can be a HashMap<UniqueName,Activity>. This would optimize the ConsoleDialogPrinter
    private final ArrayList<Activity> allActivities = new ArrayList<>();

    /**
     * Contains all items which were selected by the user.
     */
    private final ShoppingCart shoppingCart = new ShoppingCart();

    /**
     * Gets the specified activity from the Arraylist.
     * @param index The Arraylist's index.
     * @return Gives the activity of the index.
     */
    public Activity getActivity(int index) {
        return allActivities.get(index);
    }

    public ArrayList<Activity> getAllActivities() {
        return allActivities;
    }

    /**
     * Adds an specified activity.
     * @param activity The activity with their own values.
     */
    public void addActivity(Activity... activity) {
       this.allActivities.addAll(Arrays.asList(activity));
    }

    /**
     * Gets a ShoppingCart.
     * @return Gives the content of the ShoppingCart.
     */
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
}
