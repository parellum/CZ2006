package com.example.fitrition.control;

import androidx.annotation.NonNull;

import com.example.fitrition.boundary.FriendActivity;
import com.example.fitrition.entities.Friend;
import com.example.fitrition.entities.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public ArrayList<String> getFriendIDList(){
        ArrayList<String> returnArr=new ArrayList<String>();
            for (Friend aFriend:friendList){
                returnArr.add(aFriend.getUserID());
            }
            return returnArr;
    }

    /**
     * Adds Friend object to FriendList
     * @param aFriend
     * @return 1 if successful otherwise 0
     */
    public void addFriend(Friend aFriend,FriendActivity friendActivity){
        if (!friendList.contains(aFriend)){
            IndividualUserManager individualUserManager;
            friendList.add(aFriend);
            individualUserManager = IndividualUserManager.getInstance();
            mDatabaseReference=FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
            //replace arraylist in uid
            individualUserManager.getUser().getFriendList().add(aFriend.getUserID());
            mDatabaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("friendList").setValue(individualUserManager.getUser().getFriendList()).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        IndividualUserManager individualUserManager;
        if (friendList.contains(aFriend)){
            friendList.remove(aFriend);
            individualUserManager = IndividualUserManager.getInstance();
            mDatabaseReference=FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
            //replace arraylist in uid
            individualUserManager.getUser().getFriendList().remove(aFriend.getUserID());
            mDatabaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("friendList").setValue(individualUserManager.getUser().getFriendList()).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        mDatabaseReference.child(aFriend.getUserID()).child("imageUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!friendList.contains(aFriend)){
                    mDatabaseReference.removeEventListener(this);
                }
                String aImageUrl=snapshot.getValue(String.class);
                aFriend.setImageUrl(aImageUrl);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadFriendList(){
        IndividualUserManager individualUserManager;
        individualUserManager = IndividualUserManager.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot friendChild:snapshot.getChildren()) {
                    Friend targetFriend = friendChild.getValue(Friend.class);
                    if (individualUserManager.getUser().getFriendList().contains(targetFriend.getUserID()) & !getFriendIDList().contains(targetFriend.getUserID())) {
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
