package com.example.applicationskeleton.control;

import com.example.applicationskeleton.entities.Friend;
import com.example.applicationskeleton.entities.Status;

import java.util.ArrayList;

/**
 * FriendManager in charge of talking with UI to display relevant information about friends of IndividualUser
 * @author Jacques
 * @version 1.0
 * @since 20-03-2022
 */
public class FriendManager {
    /**
     * For singleton pattern adherence. This FriendManager instance persists throughout runtime.
     */
    private static FriendManager instance=null;
    /**
     * Holds ArrayList of Friend object that can be referenced throughout runtime.
     */
    ArrayList<Friend> friendList=new ArrayList<Friend>();
    /**
     * Default constructor for Friend Manager.
     */
    public FriendManager() {
        friendList=new ArrayList<Friend>();
    }
    /**
     * For singleton pattern adherence.
     * @return instance The static instance that persists throughout runtime.
     */
    public static FriendManager getInstance() {
        if (instance == null) {
            instance = new FriendManager();
        }
        return instance;
    }

    public ArrayList<Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<Friend> friendList) {
        this.friendList = friendList;
    }

    /**
     * Adds Friend object to FriendList
     * @param aFriend
     * @return 1 if successful otherwise 0
     */
    public int addFriend(Friend aFriend){
        if (!friendList.contains(aFriend)){
            friendList.add(aFriend);
            return 1;
        }
        else{
            return 0;
        }
    }

    /**
     * Removes friend object in FriendList
     * @param aFriend
     * @return 1 if successful otherwise 0
     */
    public int delFriend(Friend aFriend){
        if (friendList.contains(aFriend)){
            friendList.remove(aFriend);
            return 1;
        }
        else{
            return 0;
        }
    }

    /**
     * Returns consolidated social status of all friends and own user sorted by time
     */
    public void getConsolidatedSocialStatus(){
        for (Friend aFreind:friendList){
            ArrayList<Status> consolStatus = new ArrayList<Status>();

        }
    }

    /**
     * Saves to firebase database
     * @return 1 if successful otherwise 0
     */
    public int saveToDB(){
        return 0;
    }

    /**
     * Loads from firebase database
     * @return 1 if successful otherwise 0
     */
    public int loadToDB(){
        return 0;
    }
}
