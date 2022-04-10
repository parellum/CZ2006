package com.example.fitrition.control;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fitrition.FriendActivity;
import com.example.fitrition.entities.Friend;
import com.example.fitrition.entities.IndividualUser;
import com.example.fitrition.entities.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
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
    private DatabaseReference mDatabaseReference;
    private ProfileManager profileManager;
    /**
     * Holds ArrayList of Friend object that can be referenced throughout runtime.
     */
    private ArrayList<Friend> friendList=new ArrayList<Friend>();
    private Friend friend;
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

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    /**
     * Adds Friend object to FriendList
     * @param aFriend
     * @return 1 if successful otherwise 0
     */
    public void addFriend(Friend aFriend,FriendActivity friendActivity){
        if (!friendList.contains(aFriend)){
            friendList.add(aFriend);
            profileManager=ProfileManager.getInstance();
            mDatabaseReference=FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
            //replace arraylist in uid
            profileManager.getUser().getFriendList().add(aFriend.getUserID());
            mDatabaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("friendList").setValue(profileManager.getUser().getFriendList()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    friendActivity.friendAddToggle();
                }
            });
            friend.getFriendList().add(FirebaseAuth.getInstance().getCurrentUser().getUid());
            mDatabaseReference.child(aFriend.getUserID()).child("friendList").setValue(friend.getFriendList());

            return;
        }
        else{
            return;
        }
    }

    public void removeFriend(Friend aFriend,FriendActivity friendActivity){
        if (friendList.contains(aFriend)){
            friendList.remove(aFriend);
            profileManager=ProfileManager.getInstance();
            mDatabaseReference=FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
            //replace arraylist in uid
            profileManager.getUser().getFriendList().remove(aFriend.getUserID());
            mDatabaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("friendList").setValue(profileManager.getUser().getFriendList()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    friendActivity.friendAddToggle();
                }
            });
            friend.getFriendList().remove(FirebaseAuth.getInstance().getCurrentUser().getUid());
            mDatabaseReference.child(aFriend.getUserID()).child("friendList").setValue(friend.getFriendList());
            return;
        }
        else{
            return;
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

    public void friendListener(Friend aFriend){
            mDatabaseReference= FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
            mDatabaseReference.child(aFriend.getUserID()).child("socialStatus").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!friendList.contains(aFriend)){
                        mDatabaseReference.removeEventListener(this);
                    }
                    ArrayList<Status> newStatusList=new ArrayList<Status>();
                    for (DataSnapshot snap:snapshot.getChildren()){
                        Status aStatus=snap.getValue(Status.class);
                        newStatusList.add(aStatus);
                    }
                    aFriend.setSocialStatus(newStatusList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            mDatabaseReference.child(aFriend.getUserID()).child("description").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!friendList.contains(aFriend)){
                        mDatabaseReference.removeEventListener(this);
                    }
                    String aDescription=snapshot.getValue(String.class);
                    aFriend.setDescription(aDescription);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    public void loadFriendList(){
        profileManager=ProfileManager.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot friendChild:snapshot.getChildren()) {
                    Friend targetFriend = friendChild.getValue(Friend.class);
                    if (profileManager.getUser().getFriendList().contains(targetFriend.getUserID()) & !friendList.contains(targetFriend.getUserID())) {
                        friendList.add(targetFriend);
                        friendListener(targetFriend);
                    }
                }
                mDatabaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }
}
