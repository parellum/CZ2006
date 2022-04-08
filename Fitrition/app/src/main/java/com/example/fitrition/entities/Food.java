package com.example.fitrition.entities;

import android.media.Image;

//Don't know who created this but i'm using it
public class Food {
    private int foodID;
    private String nameOfFood;
    private String nameOfStall;
    private String description;
    private double calories;
    private String foodImageUrl;

    public Food(int foodID, String nameOfFood, String nameOfStall, String description, double calories, String foodImageUrl) {
        this.foodID = foodID;
        this.nameOfFood = nameOfFood;
        this.nameOfStall = nameOfStall;
        this.description = description;
        this.calories = calories;
        this.foodImageUrl = foodImageUrl;
    }

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

}
