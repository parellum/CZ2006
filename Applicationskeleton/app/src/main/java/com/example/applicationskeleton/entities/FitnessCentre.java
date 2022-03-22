package com.example.applicationskeleton.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class FitnessCentre extends Facility{
    private int facilityID;
    private String name;
    private String description;
    private int postalCode;
    private LocalDateTime openingHour;
    private String contactNumber;
    private String fitnessCentreAddress;
    private int numberOfClicks;
    private ArrayList<Integer> ratingScoreList;
    private int numTotalRating;
    private ArrayList<Review> reviewList;
}
