package com.example.fitrition.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Fitness extends Facility{
    public Fitness(String facilityID, String name, String description, int postalCode, LocalDateTime openingHour, String contactNumber, String priceRange, String emailAddress, String password, String eateryAddress, int numberOfClicks, ArrayList<Integer> ratingScoreList, int numTotalRating, ArrayList<Review> reviewList) {
        super(facilityID, name, description, postalCode, openingHour, contactNumber, priceRange, emailAddress, password, eateryAddress, numberOfClicks, ratingScoreList, numTotalRating, reviewList);
    }
    /**Additional attributes **/
}
