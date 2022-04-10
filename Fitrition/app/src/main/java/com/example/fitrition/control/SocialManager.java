package com.example.fitrition.control;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.fitrition.entities.Achievement;
import com.example.fitrition.entities.Events;
import com.example.fitrition.entities.Friend;
import com.example.fitrition.entities.IndividualUser;
import com.example.fitrition.entities.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;


public class SocialManager {
    private static SocialManager instance=null;
    private ArrayList<Status> friendStatusList;
    private ArrayList<Status> personalStatusList;

    DatabaseReference mDatabaseReference;
    FriendManager friendManager;

    public SocialManager() {
        friendStatusList=new ArrayList<Status>();
        personalStatusList=new ArrayList<Status>();
    }

    public static SocialManager getInstance() {
        if (instance == null) {
            instance = new SocialManager();
        }
        return instance;
    }

    public ArrayList<Status> getFriendStatusList() {
        return friendStatusList;
    }

    public void setFriendStatusList(ArrayList<Status> friendStatusList) {
        this.friendStatusList = friendStatusList;
    }

    public ArrayList<Status> getPersonalStatusList() {
        return personalStatusList;
    }

    public void setPersonalStatusList(ArrayList<Status> personalStatusList) {
        this.personalStatusList = personalStatusList;
    }

    public void createStatusFromEvent(Events event){
        loadPersonalStatusList();
        Status newStatus=new Status();
        newStatus.setDay(event.getDate());
        newStatus.setYear(event.getYear());
        newStatus.setMonth(event.getMonth());
        newStatus.setTime(event.getTime());
        ProfileManager profileManager=ProfileManager.getInstance();
        if (event.getExercise()==true){
            newStatus.setDescription(profileManager.getUser().getName()+" exercised "+event.getEvent()+" at "+event.getLocation());
        }else{
            newStatus.setDescription(profileManager.getUser().getName()+" ate "+event.getEvent()+" at "+event.getLocation());
        }
        personalStatusList.add(newStatus);
        FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("socialStatus").setValue(personalStatusList);
    }

    public void loadFriendStatusList(){
        friendManager =FriendManager.getInstance();
        friendStatusList = new ArrayList<Status>();
        ArrayList<String> friendIDList=new ArrayList<String>();
        ArrayList<Status> unsortedArr=new ArrayList<Status>();
        ArrayList<Long> sortArr=new ArrayList<Long>();
        for (Friend friendSub:friendManager.getFriendList()){
            friendIDList.add(friendSub.getUserID());
            for (Status statusSub:friendSub.getSocialStatus()){
                unsortedArr.add(statusSub);
                sortArr.add(Long.parseLong(statusSub.getYear()+statusSub.getMonth()+statusSub.getDay()+statusSub.getTime()));
            }
        }
        Collections.sort(sortArr);
        Collections.reverse(sortArr);
        for (Long subject:sortArr){
            Log.d(TAG, "loadFriendStatusList: "+subject.toString());
            for (Status sub:unsortedArr){
                if (subject.toString().equals(sub.getYear()+sub.getMonth()+sub.getDay()+sub.getTime())){
                    friendStatusList.add(sub);
                    unsortedArr.remove(sub);
                    break;
            }
            }
        }
    }

    public void loadPersonalStatusList(){
        ProfileManager profileManager = ProfileManager.getInstance();
        personalStatusList = new ArrayList<Status>();
        ArrayList<Status> unsortedArr=new ArrayList<Status>();
        ArrayList<Long> sortArr=new ArrayList<Long>();
        for (Status statusSub:profileManager.getUser().getSocialStatus()) {
            unsortedArr.add(statusSub);
            sortArr.add(Long.parseLong(statusSub.getYear() + statusSub.getMonth() + statusSub.getDay() + statusSub.getTime()));
        }
        Collections.sort(sortArr);
        Collections.reverse(sortArr);
        for (Long subject:sortArr){
            for (Status sub:unsortedArr){
                if (subject.toString().equals(sub.getYear()+sub.getMonth()+sub.getDay()+sub.getTime())){
                    personalStatusList.add(sub);
                    unsortedArr.remove(sub);
                    break;
                }
            }
        }
    }

    public ArrayList<Status> getStatusOrdered(ArrayList<Status> statusOrder){
        ArrayList<Status> unsortedArr=new ArrayList<Status>();
        ArrayList<Long> sortArr=new ArrayList<Long>();
        ArrayList<Status> retArr=new ArrayList<Status>();
        for (Status statusSub:statusOrder) {
            unsortedArr.add(statusSub);
            sortArr.add(Long.parseLong(statusSub.getYear() + statusSub.getMonth() + statusSub.getDay() + statusSub.getTime()));
        }
        Collections.sort(sortArr);
        Collections.reverse(sortArr);
        for (Long subject:sortArr){
            for (Status sub:unsortedArr){
                if (subject.toString().equals(sub.getYear()+sub.getMonth()+sub.getDay()+sub.getTime())){
                    retArr.add(sub);
                    unsortedArr.remove(sub);
                    break;
                }
            }
        }
        return retArr;
    }
}
