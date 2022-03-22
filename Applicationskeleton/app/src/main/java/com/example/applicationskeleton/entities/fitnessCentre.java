package com.example.applicationskeleton.entities;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class fitnessCentre extends Facility{
    private String facility_name;
    private int postalCode;
    private LocalDateTime openingHour;
    private String contactNumber;
    private int numTotalRating;
    private ArrayList<Integer> ratingArrayList;
    private ArrayList<Review> reviewArrayList;

    public fitnessCentre(String facility_name, int postalCode, LocalDateTime openingHour, String contactNumber, int numTotalRating) {
        this.facility_name = facility_name;
        this.postalCode = postalCode;
        this.openingHour = openingHour;
        this.contactNumber = contactNumber;
        this.numTotalRating = numTotalRating;
        this.ratingArrayList = new ArrayList<Integer>();
        this.reviewArrayList = new ArrayList<Review>();
    }

    public String getName() {
        return facility_name;
    }

    public void setName(String facility_name) {
        this.facility_name = facility_name;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public LocalDateTime getOpeningHour() {
        return this.openingHour;
    }

    public void setOpeningHour(LocalDateTime openingHour) {
        this.openingHour = openingHour;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public ArrayList<Integer> getRating() {
        return ratingArrayList;
    }

    public int getNumTotalRating() {
        return numTotalRating;
    }

    public void setNumTotalRating(int numTotalRating) {
        this.numTotalRating = numTotalRating;
    }
}