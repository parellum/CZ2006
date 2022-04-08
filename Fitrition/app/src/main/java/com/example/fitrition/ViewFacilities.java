package com.example.fitrition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.fitrition.boundary.SocialFragment;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_facilities);
        viewFacilitiesButton = (Button) findViewById(R.id.viewFacilitiesButton);

        //If its a fitness facility, hide the button
        Bundle bundle = getIntent().getExtras();
        String nameOfMarkerClicked = bundle.getString("facilitiesName");

        //Remove once JAC IS DONE============================================================
        ArrayList<FitnessCentreJSON> fitnessCentreArrayList = null;
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(this.getAssets().open("fitnessLocations.txt")));
            Gson gson =  new GsonBuilder().create();
            fitnessCentreArrayList = gson.fromJson(reader, new TypeToken<List<FitnessCentreJSON>>(){}.getType()
            );

            Log.d("Sucess123", "what Inner" + Integer.toString(fitnessCentreArrayList.size()));
        }
        catch(Exception e) {
            Log.d("Failure", "Exception"  );
        }
        //==============================
        for(FitnessCentreJSON info : fitnessCentreArrayList){
            if(nameOfMarkerClicked.equalsIgnoreCase(info.getName())){
                //MATCH QUICK HIDE THE BUTTON
                viewFacilitiesButton.setVisibility(View.INVISIBLE);
                break;
            }
        }



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
