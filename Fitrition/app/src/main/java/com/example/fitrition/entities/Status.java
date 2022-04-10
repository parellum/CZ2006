package com.example.fitrition.entities;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDateTime;

public class Status {
    private String userID;
    private String description;
    private String time;
    private String day;
    private String month;
    private String year;

    public Status(){
        this.userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public Status(String description, String year, String month, String day, String time) {
        this.userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.description = description;
        this.year=year;
        this.month=month;
        this.day=day;
        this.time=time;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
