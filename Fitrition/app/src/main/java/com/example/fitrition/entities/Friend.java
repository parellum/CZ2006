package com.example.fitrition.entities;

import java.util.ArrayList;

/**
 * Friend holds certain attributes of user which are open for public consumption
 * @author Jacques
 * @version 1.0
 * @since 20-03-2022
 */
public class Friend{
    private String firstName;
    private String lastName;
    private String description;
    private Status[] socialStatusList=new Status[20];
    private ArrayList<Achievement> acheivementList;

    /**
     * Constructor for Friend class
     * @param firstName First Name of Friend
     * @param lastName Last Name of Friend
     * @param description Description of Friend
     * @param socialStatusList SocialStatusList ascending in order for timeline
     * @param acheivementList List of achievements
     */
    public Friend(String firstName, String lastName, String description, Status[] socialStatusList, ArrayList<Achievement> acheivementList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.socialStatusList = socialStatusList;
        this.acheivementList = acheivementList;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status[] getSocialStatusList() {
        return socialStatusList;
    }

    public void setSocialStatusList(Status[] socialStatusList) {
        this.socialStatusList = socialStatusList;
    }

    public ArrayList<Achievement> getAchievementList() {
        return acheivementList;
    }

    public void setAcheivementList(ArrayList<Achievement> acheivementList) {
        this.acheivementList = acheivementList;
    }
}
