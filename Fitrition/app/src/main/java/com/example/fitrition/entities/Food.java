package com.example.fitrition.entities;

import android.media.Image;

public class Food {
    private int foodID;
    private String name;
    private String description;
    private int calories;
    private Image photos;

    public int getFoodID(){
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories(){
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public Image getPhotos(){
        return photos;
    }

    public void setPhotos(Image photos) {
        this.photos = photos;
    }

    public void display() {
        System.out.print(String.format("%-30s%-75s%-10.2f", name, description, calories, photos));
    }
}
