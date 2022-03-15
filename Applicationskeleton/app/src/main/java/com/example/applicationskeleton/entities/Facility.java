package com.example.applicationskeleton.entities;


import java.time.LocalDateTime;
import java.util.ArrayList;

public class Facility {
    private int facilityID;
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
    private int numTotalRating int;
    private ArrayList<Review> reviewList;
}
