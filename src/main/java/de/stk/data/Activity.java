package de.stk.data;

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
     */
    private ActivityDates activityDates;

    /**
     * A helper object that contains all pricing information.
     */
    private ActivityPricing pricing;

    /**
     * The subclasses unique name.
     */
    private String typeName;

    /**
     * Creates an Activity.
     *
     * @param activityDates All dates the Activity will happen at stored as an {@link ActivityDates} list.
     *                      Each date also holds information about how many tickets are available.
     * @param pricing       Different pricing classes stored as an {@link ActivityPricing} object.
     */
    public Activity(ActivityDates activityDates, ActivityPricing pricing) {
        this.activityDates = activityDates;
        this.pricing = pricing;
        typeName = setTypeName();
    }

    public ActivityDates getActivityDates() {
        return activityDates;
    }

    public ActivityPricing getPricing() {
        return pricing;
    }

    public abstract String getSummary();

    public abstract String getInformation();

    /**
     * This abstract method forces all subclasses to set some value to typeName as it is invoked
     * in this classes constructor to set the typeName field.
     * A unique name should be used.
     *
     * @return The future value of typeName.
     */
    public abstract String setTypeName();

    /**
     * Returns the unique value of typeName.
     *
     * @return value of typeName.
     */
    public String getTypeName() {
        return typeName;
    }
}
