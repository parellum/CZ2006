package com.example.fitrition;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.fitrition.control.FacilityManager;
import com.example.fitrition.entities.Fitness;
import com.example.fitrition.entities.HawkerCentre;

import java.util.Objects;


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

        Bundle b = getActivity().getIntent().getExtras();

        String facilitiesName = null;
        if(b != null){
            Log.d("Tag", String.valueOf(this.getActivity().getClass()));

            facilitiesName = b.getString("facilitiesName");


            if(String.valueOf(this.getActivity().getClass()).equalsIgnoreCase("class com.example.fitrition.ViewFitnessActivity")){
                Fitness temp = FacilityManager.getInstance().searchFitness(facilitiesName);

                //Set name
                viewFacilitiesName.setText(temp.getName());

                //Set rating
                viewFacilitiesRatingBar.setRating((float) temp.getRating());

                //Set Description
                viewFacilitiesDescription.setText(temp.getDescription());

                //set time
                String paddedOpeningTime = String.format("%04d" , temp.getOpeningTime());
                String paddedEndingTime = String.format("%04d" , temp.getClosingTime());

                viewFacilitiesOpeningHr.setText(paddedOpeningTime);
                viewFacilitiesClosingHr.setText(paddedEndingTime);
            }else{
                HawkerCentre temp = FacilityManager.getInstance().searchHawker(facilitiesName);

                //Set name
                viewFacilitiesName.setText(temp.getName());

                //Set rating
                viewFacilitiesRatingBar.setRating((float) temp.getRating());

                //Set Description
                viewFacilitiesDescription.setText(temp.getDescription());

                //set time
                String paddedOpeningTime = String.format("%04d" , temp.getOpeningTime());
                String paddedEndingTime = String.format("%04d" , temp.getClosingTime());

                viewFacilitiesOpeningHr.setText(paddedOpeningTime);
                viewFacilitiesClosingHr.setText(paddedEndingTime);
            }

            }
        return view;

    }
}
