package com.example.fitrition.control;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fitrition.entities.Facility;
import com.example.fitrition.entities.FitnessCentreJSON;
import com.example.fitrition.entities.Review;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Vector;

public class FacilityManager {
    private static FacilityManager instance=null;
    //private ArrayList<Facility> facilityList;
    private ArrayList<Review> reviewList;
    private final float distanceInKm = 2;



    public static FacilityManager getInstance() {
        if (instance == null) {
            instance = new FacilityManager();
        }
        return instance;
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

}




