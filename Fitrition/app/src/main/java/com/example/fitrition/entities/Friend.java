package com.example.fitrition.entities;

import java.util.ArrayList;

/**
 * Friend holds certain attributes of user which are open for public consumption
 * @author Jacques
 * @version 1.0
 * @since 20-03-2022
 */
public class Friend{
    private String userID;
    private String userName;
    private ArrayList<Status> socialStatus;
    private ArrayList<String> friendRequest;
    private String description;
    private String name;
    private String dob;
    private String gender;
    private String imageUrl;

    public Friend(){

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Status> getSocialStatus() {
        return socialStatus;
    }

    public void setSocialStatus(ArrayList<Status> socialStatus) {
        this.socialStatus = socialStatus;
    }

    public ArrayList<String> getFriendRequest() {
        return friendRequest;
    }

    public void setFriendRequest(ArrayList<String> friendRequest) {
        this.friendRequest = friendRequest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
