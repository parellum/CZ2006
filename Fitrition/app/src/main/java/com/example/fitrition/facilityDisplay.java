package com.example.fitrition;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.fitrition.control.FacilityManager;
import com.example.fitrition.entities.FitnessCentreJSON;

import java.util.ArrayList;


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
        ArrayList<FitnessCentreJSON> fitnessCentre = facilityManager.getFacilityList();

        Bundle b = getActivity().getIntent().getExtras();

        String facilitiesName = null;
        if(b != null){
            facilitiesName = b.getString("facilitiesName");

            for(int i = 0; i<fitnessCentre.size(); i++){
                if(fitnessCentre.get(i).getName().equalsIgnoreCase(facilitiesName)){
                    //Set name
                    viewFacilitiesName.setText(fitnessCentre.get(i).getName());

                    //Set rating
                    viewFacilitiesRatingBar.setRating((float) fitnessCentre.get(i).getRating());

                    //Set Description
                    viewFacilitiesDescription.setText(fitnessCentre.get(i).getDescription());

                    //set time
                    String paddedOpeningTime = String.format("%04d" , fitnessCentre.get(i).getOpeningTime());
                    String paddedEndingTime = String.format("%04d" , fitnessCentre.get(i).getClosingTime());

                    viewFacilitiesOpeningHr.setText(paddedOpeningTime);
                    viewFacilitiesClosingHr.setText(paddedEndingTime);

                }
            }
        }

        return view;

    }
}