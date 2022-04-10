package com.example.fitrition.entities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fitrition.ViewFitnessActivity;
import com.example.fitrition.ViewHawkerActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewFacilitiesActivityFactory {

    private static ViewFacilitiesActivity viewHawkerActivity = null;//new ViewHawkerActivity();
    private static ViewFacilitiesActivity viewFitnessActivity = null;

    public static ViewFacilitiesActivity getFacility(String type){
        ViewFacilitiesActivity f = null;

        if(type.equalsIgnoreCase("Hawker")){
            if(viewHawkerActivity == null){
                viewHawkerActivity = new ViewHawkerActivity();
            }

            f = viewHawkerActivity;
        }else{
            if(viewFitnessActivity == null){
                viewFitnessActivity = new ViewFitnessActivity();
            }

            f = viewFitnessActivity;

        }
        return f;
    }


}