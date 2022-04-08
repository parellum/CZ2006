package com.example.fitrition;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.fitrition.control.FacilityManager;
import com.example.fitrition.entities.FitnessCentreJSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class facilityDisplay extends Fragment {
    private TextView viewFacilitiesName;
    private RatingBar viewFacilitiesRatingBar;
    private TextView viewFacilitiesDescription;
    private TextView viewFacilitiesOpeningHr;
    private TextView viewFacilitiesClosingHr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facility_display, container, false);

        viewFacilitiesName = (TextView) view.findViewById(R.id.viewFacilitiesName);
        viewFacilitiesRatingBar = (RatingBar) view.findViewById(R.id.viewFacilitiesRatingBar);
        viewFacilitiesDescription = (TextView) view.findViewById(R.id.viewFacilitiesDescription);
        viewFacilitiesOpeningHr = (TextView) view.findViewById(R.id.viewFacilitiesOpeningHour);
        viewFacilitiesClosingHr = (TextView) view.findViewById(R.id.viewFacilitiesClosingHour);

        //Version 2 using facilities manager
        FacilityManager facilityManager= FacilityManager.getInstance();

        //ArrayList<FitnessCentreJSON> fitnessCentre = facilityManager.getFacilityList();
        //Remove once JAC IS DONE============================================================
        ArrayList<FitnessCentreJSON> allFacilitiesArrayList = null;
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getActivity().getApplicationContext().getAssets().open("sampleJson.txt")));
            Gson gson =  new GsonBuilder().create();
            allFacilitiesArrayList = gson.fromJson(reader, new TypeToken<List<FitnessCentreJSON>>(){}.getType()
            );

            Log.d("Sucess123", "what Inner" + Integer.toString(allFacilitiesArrayList.size()));
        }
        catch(Exception e) {
            Log.d("Failure", "Exception"  );
        }
        //==============================

        Bundle b = getActivity().getIntent().getExtras();

        String facilitiesName = null;
        if(b != null){
            facilitiesName = b.getString("facilitiesName");
            for(int i = 0; i<allFacilitiesArrayList.size(); i++){
                if(allFacilitiesArrayList.get(i).getName().equalsIgnoreCase(facilitiesName)){
                    //Set name
                    viewFacilitiesName.setText(allFacilitiesArrayList.get(i).getName());

                    //Set rating
                    viewFacilitiesRatingBar.setRating((float) allFacilitiesArrayList.get(i).getRating());

                    //Set Description
                    viewFacilitiesDescription.setText(allFacilitiesArrayList.get(i).getDescription());

                    //set time
                    String paddedOpeningTime = String.format("%04d" , allFacilitiesArrayList.get(i).getOpeningTime());
                    String paddedEndingTime = String.format("%04d" , allFacilitiesArrayList.get(i).getClosingTime());

                    viewFacilitiesOpeningHr.setText(paddedOpeningTime);
                    viewFacilitiesClosingHr.setText(paddedEndingTime);

                }
            }
        }

        return view;

    }
}