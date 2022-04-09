package com.example.fitrition.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Food {

    @SerializedName("foodID")
    @Expose
    private int foodID;

    @SerializedName("nameOfFood")
    @Expose
    private String nameOfFood;

    @SerializedName("nameOfStall")
    @Expose
    private String nameOfStall;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("calories")
    @Expose
    private double calories;

    @SerializedName("foodImageUrl")
    @Expose
    private String foodImageUrl;

    @SerializedName("nameOfHawker")
    @Expose
    private String nameOfHawker;

    public Food(int foodID, String nameOfFood, String nameOfHawker, String nameOfStall, String description, double calories, String foodImageUrl) {
        this.foodID = foodID;
        this.nameOfFood = nameOfFood;
        this.nameOfHawker = nameOfHawker;
        this.description = description;
        this.calories = calories;
        this.foodImageUrl = foodImageUrl;
    }
    public Food(){}

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public String getNameOfFood() {
        return nameOfFood;
    }

    public void setNameOfFood(String nameOfFood) {
        this.nameOfFood = nameOfFood;
    }

    public String getNameOfStall() {
        return nameOfStall;
    }

    public void setNameOfStall(String nameOfStall) {
        this.nameOfStall = nameOfStall;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getFoodImageUrl() {
        return foodImageUrl;
    }

    public void setFoodImageUrl(String foodImageUrl) {
        this.foodImageUrl = foodImageUrl;
    }

    public String getNameOfHawker() {
        return nameOfHawker;
    }

    public void setNameOfHawker(String nameOfHawker) {
        this.nameOfHawker = nameOfHawker;
    }



}
