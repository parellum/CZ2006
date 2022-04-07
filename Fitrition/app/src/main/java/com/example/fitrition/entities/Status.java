package com.example.fitrition.entities;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDateTime;

/**
 * Status message which holds display message as well as time as marker
 * @author Jacques
 * @version 1.0
 * @since 20-03-2022
 */
public class Status {
    private String userID;
    private String description;
    private String time;

    public Status(){
    }

    public Status(String description, LocalDateTime localDateTime) {
        this.userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.description = description;
        this.time=localDateTime.plusHours(8).toString();
        Log.d(TAG, "Status: "+time);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
