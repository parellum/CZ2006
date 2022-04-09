package com.example.fitrition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.fitrition.boundary.SocialFragment;
import com.example.fitrition.control.FacilityManager;
import com.example.fitrition.entities.FitnessCentreJSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ViewFacilities extends AppCompatActivity {

    private ImageView viewFacilitiesImage;
    private Button viewFacilitiesButton;
    private FacilityManager facilityManager;
    private ArrayList<FitnessCentreJSON> allFacilitiesArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_facilities);
        viewFacilitiesButton = (Button) findViewById(R.id.viewFacilitiesButton);
        viewFacilitiesImage = (ImageView) findViewById(R.id.viewFacilitiesImage);



        //Default fragment being contained is the facilityDisplay

        //If its a fitness facility, hide the button
        Bundle bundle = getIntent().getExtras();
        String nameOfMarkerClicked = bundle.getString("facilitiesName");



        facilityManager = FacilityManager.getInstance();
        allFacilitiesArrayList = facilityManager.getFacilityList();

        for(FitnessCentreJSON info : allFacilitiesArrayList){
            if(info.getType().equalsIgnoreCase("FITNESS") && nameOfMarkerClicked.equalsIgnoreCase(info.getName())){
                viewFacilitiesButton.setVisibility(View.INVISIBLE);
                break;
            }
        }

        Glide.with(this)
                .load(facilityManager.searchFacility(nameOfMarkerClicked).getImageUrl())
                .into(viewFacilitiesImage);

        viewFacilitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.viewFacilitiesFragmentContainer, viewingOfHealthyFood.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // name can be null
                        .commit();
            }
        });


        }
    }
