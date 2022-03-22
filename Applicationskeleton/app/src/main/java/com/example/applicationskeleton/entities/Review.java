package com.example.applicationskeleton.entities;

import java.time.LocalDateTime;

public class Review {
    private String reviewID;
    private String userID;
    private String facilityID;
    private int rating;
    private String content;
    private LocalDateTime date;

    public Review(String reviewID, String userID, String facilityID, int rating, String content, LocalDateTime date) {
        this.reviewID = reviewID;
        this.userID = userID;
        this.facilityID = facilityID;
        this.rating = rating;
        this.content = content;
        this.date = date;
    }

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
