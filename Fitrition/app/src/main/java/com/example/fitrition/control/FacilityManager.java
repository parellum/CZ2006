package com.example.fitrition.control;
import static android.content.ContentValues.TAG;

import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fitrition.entities.Facility;
import com.example.fitrition.entities.Fitness;
import com.example.fitrition.entities.FitnessCentreJSON;
import com.example.fitrition.entities.Food;
import com.example.fitrition.entities.Review;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Vector;

public class FacilityManager {
    private static FacilityManager instance=null;
    private ArrayList<FitnessCentreJSON> facilityList;

    public ArrayList<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(ArrayList<Food> foodList) {
        this.foodList = foodList;
    }

    private ArrayList<Food> foodList;
    private final float distanceInKm = 2;

    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;

    public  FacilityManager(){
        facilityList = new ArrayList<FitnessCentreJSON>();
    }

    public static FacilityManager getInstance() {
        if (instance == null) {
            instance = new FacilityManager();
        }
        return instance;
    }

    public ArrayList<FitnessCentreJSON> getFacilityList() {
        return facilityList;
    }

    public void setFacilityList(ArrayList<FitnessCentreJSON> facilityList) {
        this.facilityList = facilityList;
    }

    public void obtainFacilityBasedOnDistance(Vector<MarkerOptions> markerOptions, double latCur, double lngCur) {

        Vector<MarkerOptions> editedMarkerOption = new Vector<>();
        Log.d("FacilityManager","Start of obtainFacilityBasedOnDistance" + Double.toString(latCur) + " " + Double.toString(lngCur) );

        for (int i = 0; i < markerOptions.size(); i++) {
            LatLng lat2 = markerOptions.get(i).getPosition();
            //Log.d("FacilityManager","Lat" + Double.toString(lat2.latitude));

            if(distance(latCur, lat2.latitude, lngCur, lat2.longitude) <= distanceInKm){
                //editedMarkerOption.add(markerOptions.get(i));
                MarkerOptions m = new MarkerOptions();
                m = markerOptions.get(i);
                editedMarkerOption.add(m);

                //Log.d("FacilityManager","Within 2km" );
            }
        }

        markerOptions.clear();
        Log.d("FacilityManager",Integer.toString(markerOptions.size()));

        for (int i = 0; i < editedMarkerOption.size(); i++) {
            markerOptions.add(editedMarkerOption.get(i));
            Log.d("FacilityManager Added","");
        }

        Log.d("FacilityManager",Integer.toString(markerOptions.size()));
        //return markerOptions;
    }

    public double distance(double lat1, double lat2, double lon1, double lon2)
    {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        //Log.d("FacilityManager","Dist" + Double.toString(c*r));
        return(c * r);
    }

    //Add facility to rtdb
    //Add facility image to

    //Get array of facility objects
    public void getFacilityImage(FitnessCentreJSON facility){
        mDatabaseReference = FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("facilities");
        mStorageReference = FirebaseStorage.getInstance("gs://fitrition-3a967.appspot.com/").getReference("facility_images");
        String fileName = facility.getName().replace(' ', '-');
        StorageReference imageReference = mStorageReference.child(fileName
                +"."+"jpg");
        imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
            }
        });
    }

    public void addFacilities (ArrayList<FitnessCentreJSON> facilityArray){
        mDatabaseReference = FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("facilities");
        mStorageReference = FirebaseStorage.getInstance("gs://fitrition-3a967.appspot.com/").getReference("facility_images");
        for (FitnessCentreJSON facility:facilityArray){
            mDatabaseReference.child(facility.getName()).setValue(facility).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d(TAG, "onComplete: added facility"+facility.getName());
                }
            });
        }
    }

    public void loadFacilities(){
        facilityList = new ArrayList<FitnessCentreJSON>();
        mDatabaseReference = FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("facilities");
        mStorageReference = FirebaseStorage.getInstance("gs://fitrition-3a967.appspot.com/").getReference("facility_images");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childsnap:snapshot.getChildren()){
                    FitnessCentreJSON facility = childsnap.getValue(FitnessCentreJSON.class);
                    facilityList.add(facility);
                    /*mStorageReference.child(facility.getName().replace(' ', '-') + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            facility.setImageUrl(uri.toString());
                            Log.d(TAG, "onDataChange: "+facility.getImageUrl());
                            facilityList.add(facility);
                        }
                    });*/
                }
                mDatabaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadFood(){
        foodList = new ArrayList<Food>();
        mDatabaseReference = FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("foodItems");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childsnap:snapshot.getChildren()){
                    Food f = childsnap.getValue(Food.class);
                    foodList.add(f);

                }
                mDatabaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public FitnessCentreJSON searchFacility(String name){

        for(FitnessCentreJSON info : facilityList){
            if( name.equalsIgnoreCase(info.getName())){
                return info;
            }

        }

        //BB
        return null;
    }

    public void saveFacilitiesnow(){
        mDatabaseReference = FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("facilities");
        mDatabaseReference.setValue(facilityList);
    }
}




