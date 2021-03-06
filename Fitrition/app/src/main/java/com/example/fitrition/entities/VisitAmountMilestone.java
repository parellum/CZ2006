package com.example.fitrition.entities;
/**
 * VisitAmountMilestone holds certain attributes of user which are open for public consumption
 * @author Jeremy
 * @version 1.0
 * @since 20-03-2022
 */
public class VisitAmountMilestone extends Achievement {
    private int placesVisitCount;

    /**
     * Constructor for VisitAmountMilestone
     * @param
     */
    public VisitAmountMilestone() {
    }
    public int getPlacesVisitCount() {
        return this.placesVisitCount;
    }

    /**
     *
     * @param placesVisitCount
     */

    public void setPlacesVisitCount(int placesVisitCount) {
        this.placesVisitCount = placesVisitCount;
    }

}
