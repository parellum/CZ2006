package com.example.fitrition.entities;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FitnessCentre extends Facility{
    private String facility_name;
    private int postalCode;
    private LocalDateTime openingHour;
    private String contactNumber;
    private int numTotalRating;
    private ArrayList<Integer> ratingArrayList;
    private ArrayList<Review> reviewArrayList;

    public FitnessCentre(String facilityID, String name, String description, int postalCode, LocalDateTime openingHour, String contactNumber, String priceRange, String emailAddress, String password, String eateryAddress, int numberOfClicks, ArrayList<Integer> ratingScoreList, int numTotalRating, ArrayList<Review> reviewList, String facility_name, int postalCode1, LocalDateTime openingHour1, String contactNumber1, int numTotalRating1, ArrayList<Integer> ratingArrayList, ArrayList<Review> reviewArrayList) {
        super(facilityID, name, description, postalCode, openingHour, contactNumber, priceRange, emailAddress, password, eateryAddress, numberOfClicks, ratingScoreList, numTotalRating, reviewList);
        this.facility_name = facility_name;
        this.postalCode = postalCode1;
        this.openingHour = openingHour1;
        this.contactNumber = contactNumber1;
        this.numTotalRating = numTotalRating1;
        this.ratingArrayList = ratingArrayList;
        this.reviewArrayList = reviewArrayList;
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