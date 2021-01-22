package de.stk.shop;

import de.stk.data.Activity;
import lombok.Getter;

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
    @Getter
    private final ArrayList<Activity> allActivities = new ArrayList<>();

    /**
     * Contains all items which were selected by the user.
     */
    @Getter
    private final ShoppingCart shoppingCart = new ShoppingCart();

    /**
     * Adds an specified activity.
     *
     * @param activity The activity with their own values.
     */
    public void addActivity(Activity... activity) {
        this.allActivities.addAll(Arrays.asList(activity));
    }
}
