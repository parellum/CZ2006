package com.example.fitrition.entities;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Events {
    String id,event,location,time,date,month,year;
    Boolean done,isExercise;

    public Events(){}

    public Events(String event, String location, String time, String date, String month, String year, Boolean isExercise) {
        this.id = Long.toString(Calendar.getInstance().getTimeInMillis());
        this.event = event;
        this.location = location;
        this.time = time;
        this.date = date;
        this.month = month;
        this.year = year;
        this.done = false;
        this.isExercise=isExercise;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getExercise() {
        return isExercise;
    }

    public void setExercise(Boolean exercise) {
        isExercise = exercise;
    }
}
