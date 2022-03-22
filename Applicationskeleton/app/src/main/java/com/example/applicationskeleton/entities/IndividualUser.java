package com.example.applicationskeleton.entities;

import java.util.ArrayList;

/**
 * User specific for common users
 * @author Jacques
 * @version 1.0
 * @since 20-03-2022
 */
public class IndividualUser extends User{
    private Status[] socialStatusList=new Status[20];
    private ArrayList<Achievement> acheivementList;
    private ArrayList<Integer> friendList;

    /**
     * Constructor for IndividualUser
     * @param userID
     * @param description
     * @param firstName
     * @param lastName
     * @param password
     * @param isVerified
     * @param email
     * @param address
     * @param socialStatusList List of Activities in ascending time order
     * @param acheivementList ArrayList of achievements specific to user
     * @param friendList ArrayList of friends
     */
    public IndividualUser(int userID, String description, String firstName, String lastName, String password, boolean isVerified, String email, String address, Status[] socialStatusList, ArrayList<Achievement> acheivementList, ArrayList<Integer> friendList) {
        super(userID, description, firstName, lastName, password, isVerified, email, address);
        this.socialStatusList = socialStatusList;
        this.acheivementList = acheivementList;
        this.friendList = friendList;
    }

    public Status[] getSocialStatusList() {
        return socialStatusList;
    }

    public void setSocialStatusList(Status[] socialStatusList) {
        this.socialStatusList = socialStatusList;
    }

    public ArrayList<Achievement> getAcheivementList() {
        return acheivementList;
    }

    public void setAcheivementList(ArrayList<Achievement> acheivementList) {
        this.acheivementList = acheivementList;
    }

    public ArrayList<Integer> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<Integer> friendList) {
        this.friendList = friendList;
    }
}
