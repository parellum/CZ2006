package com.example.applicationskeleton.control;
import com.example.applicationskeleton.entities.Facility;
import com.example.applicationskeleton.entities.Review;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class FacilityManager {
    private static FacilityManager SINGLE_INSTANCE;
    private ArrayList<Facility> facilityList;
    private ArrayList<Review> reviewList;

    public void getFacilityList(){
        if(facilityList==null || facilityList.size()==0) {
            System.out.println(" There is no facility in the list.");
        } else {
            for (Facility facility : facilityList) {
                System.out.println("Facility Name: " + facility.getName());
                System.out.println("Facility ID : " + facility.getFacilityID());
                System.out.println("Postal Code : " + facility.getPostalCode());
            }
        }
    }

    // is there Foodcentre entity? Eatery
    public void showFitnessCentreInfo(String facilityID){
        Facility f = getFacility(facilityID);
        System.out.println("-------------------------------------------");
        System.out.println("Facility Name: " + f.getName());
        System.out.println("Opening Hour : " + f.getOpeningHour());
        System.out.println("Postal Code : " + f.getPostalCode());
        System.out.println("Contact Number : " + f.getContactNumber());
        System.out.println("Number of ratings : " + f.getNumTotalRating());
        System.out.println("Rating : " + f.getRating());
    }
    public void getMap(){

    }

    public void displayMapView(){}
    public void displayListView(){}

    //combined review and rating, user can choose to leave both review and rating or just the rating
    public void addReviewRating(String reviewID, String userID, String facilityID, int rating, String content, LocalDateTime date){
        Review review = new Review(reviewID, userID, facilityID, rating, content, date);
        reviewList.add(review);
    }

    public void deleteReview(String reviewID){
        Review r = getRevByReviewID(reviewID);
        reviewList.remove(r);
    }

    public void displayReviewByFacility(String facilityID){
        Review r = getRevByFacilityID(facilityID);
        System.out.println("-------------------------------------------");
        System.out.println("Date: " + r.getDate());
        System.out.println("User ID : " + r.getUserID());
        System.out.println("Review ID: " + r.getReviewID());
        System.out.println("Content : " + r.getContent());
        System.out.println("Rating : " + r.getRating());
    }
    //display review by userid?

    public void editReview(String reviewID, String newContent){
        getRevByReviewID(reviewID).setContent(newContent);
    }

    public void editRating(String reviewID, int newRating){
        getRevByReviewID(reviewID).setRating(newRating);
    }

    public Review filter(String keyword){
        if(reviewList==null || reviewList.size()==0) {
            System.out.println("There is no review in the list.");
        } else {
            for (Review review : reviewList) {
                if (review.getContent().toLowerCase().contains(keyword.toLowerCase())) {
                    return review;
                } else{
                    continue;
                }
            }
        }
        System.out.println("Keyword is not found.");
        return null;
    }

    //
    public void addToTracker(String postalCode, String name){

    }


    public Review getRevByReviewID(String reviewID){
        if(reviewList==null || reviewList.size()==0) {
            System.out.println("There is no review in the list.");
        } else {
            for (Review review : reviewList) {
                if (review.getReviewID().equals(reviewID)) {
                    return review;
                } else{
                    continue;
                }
            }
        }
        System.out.println("Review is not found.");
        return null;
    }

    public Review getRevByFacilityID(String facilityID){
        if(reviewList==null || reviewList.size()==0) {
            System.out.println("There is no review in the list.");
        } else {
            for (Review review : reviewList) {
                if (review.getFacilityID().equals(facilityID)) {
                    return review;
                } else{
                    continue;
                }
            }
        }
        System.out.println(" Review is not found.");
        return null;
    }
    public Facility getFacility(String facilityID){
        if(facilityList==null || facilityList.size()==0) {
            System.out.println("There is no facility in the list.");
        }
        else {
            for (Facility facility : facilityList) {
                if (facility.getFacilityID().equals(facilityID)){
                    return facility;
                }else{
                    continue;
                }
            }
        }
        System.out.println(" Facility is not found.");
        return null;
    }













}




