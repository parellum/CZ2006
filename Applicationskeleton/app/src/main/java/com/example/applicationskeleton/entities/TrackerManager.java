package com.example.applicationskeleton.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TrackerManager {
    private String activityName;
    private String activityType;
    private LocalDate date;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int burntCals;
    private String mealName;
    private String mealLocation;
    private int intakeCals;
    private int calsGained;

    public TrackerManager(String activityName, String activityType, LocalDate date, String location, LocalDateTime startTime, LocalDateTime endTime, int burntCals, String mealName, String mealLocation, int intakeCals, int calsGained) {
        this.activityName = activityName;
        this.activityType = activityType;
        this.date = date;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.burntCals = burntCals;
        this.mealName = mealName;
        this.mealLocation = mealLocation;
        this.intakeCals = intakeCals;
        this.calsGained = calsGained;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getBurntCals() {
        return burntCals;
    }

    public void setBurntCals(int burntCals) {
        this.burntCals = burntCals;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealLocation() {
        return mealLocation;
    }

    public void setMealLocation(String mealLocation) {
        this.mealLocation = mealLocation;
    }

    public int getIntakeCals() {
        return intakeCals;
    }

    public void setIntakeCals(int intakeCals) {
        this.intakeCals = intakeCals;
    }

    public int getCalsGained() {
        return calsGained;
    }

    public void setCalsGained(int calsGained) {
        this.calsGained = calsGained;
    }
}
