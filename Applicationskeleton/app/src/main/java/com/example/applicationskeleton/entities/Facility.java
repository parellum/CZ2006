package com.example.applicationskeleton.entities;


import java.time.LocalDateTime;
import java.util.ArrayList;

public class Facility {
    private String facilityID; //changed from int to String
    private String name;
    private String description;
    private int postalCode;
    private LocalDateTime openingHour;
    private String contactNumber;
    private String priceRange;
    private String emailAddres;
    private String password;
    private String eateryAddress;
    private int numberOfClicks;
    private ArrayList<Integer> ratingScoreList;
    private int numTotalRating;
    private ArrayList<Review> reviewList;

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getPostalCode() { return postalCode; }

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
        return ratingScoreList;
    }

    public int getNumTotalRating() {
        return numTotalRating;
    }

    public void setNumTotalRating(int numTotalRating) {
        this.numTotalRating = numTotalRating;
    }

}
