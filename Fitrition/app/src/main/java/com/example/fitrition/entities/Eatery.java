package com.example.fitrition.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Eatery extends Facility{
    /**
     * Denotes typeOfFood served at the eatery, the foodID
     * and the list of food available in the eatery
     */

    private String typeOfFood;
    private int foodID; //do we need foodID here?
    private ArrayList<Food> listOfFood;

    /**
     * Creates a new eatery object using the following fields
     * @param facilityID
     * @param name
     * @param description
     * @param postalCode
     * @param openingHour
     * @param contactNumber
     * @param priceRange
     * @param emailAddress
     * @param password
     * @param eateryAddress
     * @param numberOfClicks
     * @param ratingScoreList
     * @param numTotalRating
     * @param reviewList
     * @param typeOfFood
     * @param foodID
     * @param listOfFood
     */
    public Eatery(String facilityID, String name, String description, int postalCode,
                  LocalDateTime openingHour, String contactNumber, String priceRange,
                  String emailAddress, String password, String eateryAddress, int numberOfClicks,
                  ArrayList<Integer> ratingScoreList, int numTotalRating,
                  ArrayList<Review> reviewList,
                  String typeOfFood, int foodID, ArrayList<Food> listOfFood){

        super(facilityID, name, description, postalCode, openingHour, contactNumber,
                priceRange, emailAddress, password, eateryAddress, numberOfClicks,
                ratingScoreList, numTotalRating, reviewList);

        this.typeOfFood = typeOfFood;
        this.foodID = foodID;
        this.listOfFood = listOfFood;
    }


    /**
     * Display all information related to the eatery
     */
    public void display() { //refer to facility display()
        super.display();
        System.out.println(String.format("%-30s%-75s%-10s", typeOfFood,foodID,listOfFood));
    }

    public void setTypeOfFood(String typeOfFood){
        this.typeOfFood = typeOfFood;
    }

    public void setFoodID(int foodID){
        this.foodID = foodID;
    }

    public void setListOfFood(ArrayList<Food> listOfFood){
        this.listOfFood = listOfFood;
    }
}

