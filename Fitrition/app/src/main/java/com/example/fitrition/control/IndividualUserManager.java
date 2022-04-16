package com.example.fitrition.control;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fitrition.entities.IndividualUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * ProfileManager is in charge of talking with UI to display the registration page, login page, and the forget password page
 * @author Teresa
 * @version 1.0
 * @since 24-03-2022
 */

public class IndividualUserManager {

    private static IndividualUserManager instance=null;
    IndividualUser user;
    DatabaseReference mDatabaseReference;
    FriendManager friendManager;

    public IndividualUserManager() {
        user=new IndividualUser();
    }

    public static IndividualUserManager getInstance() {
        if (instance == null) {
            instance = new IndividualUserManager();
        }
        return instance;
    }

    public IndividualUser getUser(){
        return user;
    }

    public void loadUser(String uid, Context context){
        mDatabaseReference = FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users").child(uid);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(IndividualUser.class);
                Log.d(TAG, "onDataChange: "+user.getName());
                friendManager = FriendManager.getInstance();
                friendManager.loadFriendList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: user not loaded");
            }
        });
    }

}