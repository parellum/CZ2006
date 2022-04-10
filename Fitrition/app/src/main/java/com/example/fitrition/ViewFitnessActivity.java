package com.example.fitrition;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.fitrition.control.FacilityManager;
import com.example.fitrition.entities.Fitness;
import com.example.fitrition.entities.ViewFacilitiesActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewFitnessActivity extends AppCompatActivity implements ViewFacilitiesActivity {


    private ImageView viewFacilitiesImage;
    private Button viewFacilitiesButton;
    private static ArrayList<Fitness> allFitnessArrayList = null;


    public ViewFitnessActivity(){
        //Initalize List
        if(allFitnessArrayList == null){
            allFitnessArrayList = new ArrayList<>();
            DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("fitness");
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot childsnap : snapshot.getChildren()) {

                        //MUST CREATE FITNESS FIRST (NOW USING Facilities)

                        //Hawker
                        Fitness fitness = childsnap.getValue(Fitness.class);
                        allFitnessArrayList.add(fitness);
                        //Log.d("Ta444g",hawkerCentre.getImageUrl());
                    }
                    mDatabaseReference.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }

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
        viewFacilitiesButton.setVisibility(View.INVISIBLE);


        Glide.with(this)
                .load(FacilityManager.getInstance().searchFitness(nameOfMarkerClicked).getImageUrl())
                .into(viewFacilitiesImage);

    }

    public static ArrayList<Fitness> getAllFitnessArrayList() {
        return allFitnessArrayList;
    }

    public static void setAllFitnessArrayList(ArrayList<Fitness> allFitnessArrayList) {
        ViewFitnessActivity.allFitnessArrayList = allFitnessArrayList;
    }
}