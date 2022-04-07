package com.example.fitrition.entities;

public class Events {
    String Event,Location,Time,Date,Month,Year;

    public Events(String event, String location, String time, String date, String month, String year) {
        Event = event;
        Location = location;
        Time = time;
        Date = date;
        Month = month;
        Year = year;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) { Location = location; }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
