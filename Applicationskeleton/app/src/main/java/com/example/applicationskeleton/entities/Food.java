package com.example.applicationskeleton.entities;

public class Food {
    private int foodID;
    private String name;
    private String description;
    private int calories;
    private Photo photos;

    public int getFoodID(){
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public int getName(){
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getDescription(){
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

    public int getPhotos(){
        return photos;
    }

    public void setPhotos(Photo photos) {
        this.photos = photos;
    }

    public void display() {
        System.out.print(String.format("%-30s%-75s%-10.2f", name, description, calories, photos));
    }
}
