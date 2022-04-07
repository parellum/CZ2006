package com.example.fitrition.entities;

import java.util.ArrayList;

/**
 * Friend holds certain attributes of user which are open for public consumption
 * @author Jacques
 * @version 1.0
 * @since 20-03-2022
 */
public class Friend{
    private int friend_image;
    private String name;
    private String description;
    private ArrayList socialStatusList=new ArrayList<Status>();
    private ArrayList<Achievement> acheivementList;
    private String friendlist_divider;

    public int getFriend_image() {
        return friend_image;
    }

    public String getFriendlist_divider() {
        return friendlist_divider;
    }

    public void setFriendlist_divider(String friendlist_divider) {
        this.friendlist_divider = friendlist_divider;
    }

    public void setFriend_image(int friend_image) {
        this.friend_image = friend_image;
    }

    /**
     * Constructor for Friend class
     * @param name Last Name of Friend
     * @param description Description of Friend
     * @param socialStatusList SocialStatusList ascending in order for timeline
     * @param acheivementList List of achievements
     */
    public Friend(int friend_image, String name, String description, ArrayList socialStatusList, ArrayList acheivementList, String divider) {
        this.friend_image = friend_image;
        this.name = name;
        this.description = description;
        this.socialStatusList = socialStatusList;
        this.acheivementList = acheivementList;
        this.friendlist_divider = friendlist_divider;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Status> getSocialStatusList() {
        return socialStatusList;
    }

    public void setSocialStatusList(ArrayList<Status> socialStatusList) {
        this.socialStatusList = socialStatusList;
    }

    public ArrayList<Achievement> getAchievementList() {
        return acheivementList;
    }

    public void setAcheivementList(ArrayList<Achievement> acheivementList) {
        this.acheivementList = acheivementList;
    }
}
