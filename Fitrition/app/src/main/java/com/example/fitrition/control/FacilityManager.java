package com.example.fitrition.control;

import android.util.Log;

import com.example.fitrition.ViewFitnessActivity;
import com.example.fitrition.ViewHawkerActivity;
import com.example.fitrition.entities.Fitness;
import com.example.fitrition.entities.Food;
import com.example.fitrition.entities.HawkerCentre;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Vector;

public class FacilityManager {
    private final float distanceInKm = 2;

    private static FacilityManager instance=null;
    private ArrayList<HawkerCentre> hawkerList;
    private ArrayList<Food> foodList;

    public  FacilityManager(){
        hawkerList = new ArrayList<HawkerCentre>();
    }

    public static FacilityManager getInstance() {
        if (instance == null) {
            instance = new FacilityManager();
        }
        return instance;
    }

    public ArrayList<HawkerCentre> getFacilityList() {
        return hawkerList;
    }

    public void setFacilityList(ArrayList<HawkerCentre> hawkerList) {
        this.hawkerList = hawkerList;
    }

    public ArrayList<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(ArrayList<Food> foodList) {
        this.foodList = foodList;
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

    //THe legit Stuff
    public HawkerCentre searchHawker(String name){

        ArrayList <HawkerCentre> hawkerList = new ViewHawkerActivity().getAllHawkerArrayList();
        for(HawkerCentre info : hawkerList){
            if( name.equalsIgnoreCase(info.getName())){
                return info;
            }

        }

        return null;
    }

    public Fitness searchFitness(String name){
        ArrayList <Fitness> fitnessList = new ViewFitnessActivity().getAllFitnessArrayList();

        for(Fitness info : fitnessList){
            if( name.equalsIgnoreCase(info.getName())){
                return info;
            }

        }

        return null;
    }

    //Return array of partial food
    public ArrayList<Food> searchForFoodOfferedByHawker(String nameOfHawkerLocation){

        ArrayList <HawkerCentre> hawkerList = new ViewHawkerActivity().getAllHawkerArrayList();
        for(HawkerCentre info : hawkerList){

            if( nameOfHawkerLocation.equalsIgnoreCase(info.getName()) && info.getFoodList() != null){
                return info.getFoodList();
            }
        }
        return null;
    }

}




