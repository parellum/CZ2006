package com.example.fitrition.entities;

import com.example.fitrition.boundary.ViewFitnessActivity;
import com.example.fitrition.boundary.ViewHawkerActivity;

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