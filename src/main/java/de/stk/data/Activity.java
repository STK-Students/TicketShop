package de.stk.data;

import lombok.Getter;

/**
 * An abstract object representing all types of activities.
 * Holds a list of all {@link ActivityDates} the activity takes place at.
 * An Activity can happen multiple times, even on the same day.
 * Each of these "instances" has it's own amount of available tickets.
 */
public abstract class Activity {

    /**
     * A helper object that contains all information about when the Activity takes place and how many tickets are
     * available per "instance" of the activity.
     * -- GETTER --
     * Fetches the {@link ActivityDates} object that holds all dates of that Activity.
     *
     * @return A {@link ActivityDates} object containing all dates.
     */
    @Getter
    private final ActivityDates activityDates;

    /**
     * A helper object that contains all pricing information.
     * -- GETTER --
     * Fetches the {@link ActivityPricing} object that holds all price classes of that Activity.
     *
     * @return A {@link ActivityPricing} object containing the different price classes of an Activity.
     *
     */
    @Getter
    ActivityPricing pricing;

    /**
     * The subclasses type name.
     * Example types: Play, Concert
     * -- GETTER --
     * Returns the unique value of typeName.
     *
     * @return Value of typeName.
     *
     */
    @Getter
    private final String typeName;

    /**
     * Creates an Activity.
     *
     * @param activityDates All dates the Activity will happen at stored as an {@link ActivityDates} object.
     *                      Each date also holds information about how many tickets are available.
     * @param pricing       Different pricing classes stored as an {@link ActivityPricing} object.
     */
    public Activity(ActivityDates activityDates, ActivityPricing pricing) {
        this.activityDates = activityDates;
        this.pricing = pricing;
        typeName = setTypeName();
    }

    /**
     * Returns a summary of the Activity as a string but does not include the price, any dates and the field that is used
     * in {@link #getUniqueName()}.
     * Do not append a new line at the end as {@link #getUniqueName()}'s result will be appended.
     *
     * @return All values of the Activity except pricing and dates.
     */
    public abstract String getSummary();

    /**
     * Calls getSummary and additionally returns the prices and dates of an Activity.
     *
     * @return The complete information of an Activity.
     */
    public abstract String getInformation();

    /**
     * Returns a unique but human readable identifier.
     * This is a plays name, a concerts topic... depending on the type.
     */
    public abstract String getUniqueName();

    /**
     * This abstract method forces all subclasses to set some value to typeName as it is invoked
     * in this classes constructor to set the typeName field.
     * A unique name should be used.
     *
     * @return The future value of typeName.
     */
    public abstract String setTypeName();
}
