package com.example.fitrition.control;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fitrition.entities.IndividualUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * ProfileManager is in charge of talking with UI to display the registration page, login page, and the forget password page
 * @author Teresa
 * @version 1.0
 * @since 24-03-2022
 */

public class ProfileManager {

    private static ProfileManager instance=null;
    IndividualUser user;
    DatabaseReference mDatabaseReference;
    StorageReference mStorageReference;

    public ProfileManager() {
        user=new IndividualUser();
    }

    public static ProfileManager getInstance() {
        if (instance == null) {
            instance = new ProfileManager();
        }
        return instance;
    }

    public void setIndividualUserList(IndividualUser user) {
        this.user = user;
    }

    public void addNewUser(String userName, String name, String eMail, String password, String DOB, String description, String gender){
        user =new IndividualUser(userName, name, eMail,  password,  DOB,  description,  gender);
    }

    public IndividualUser getUser(){
        return user;
    }

    public boolean validatePassword(String str){
        return str.matches("\\S");
    }

    public boolean validateUserName(String str){
        return str.matches("[a-zA-Z0-9]*");
    }

    public void updateIsVerified(String email, boolean isVerified){

    }

    public void loadUser(String uid, Context context){
        ArrayList<IndividualUser> lol = new ArrayList<IndividualUser>();
        mDatabaseReference = FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users").child(uid);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(IndividualUser.class);
                Log.d(TAG, "onDataChange: "+user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: user not loaded");
            }
        });
    }


}