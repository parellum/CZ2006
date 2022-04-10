package com.example.fitrition;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.fitrition.control.FacilityManager;
import com.example.fitrition.entities.Food;
import com.example.fitrition.entities.HawkerCentre;
import com.example.fitrition.entities.ViewFacilitiesActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewHawkerActivity extends AppCompatActivity implements ViewFacilitiesActivity {

    private ImageView viewFacilitiesImage;
    private Button viewFacilitiesButton;
    private static ArrayList<HawkerCentre> allHawkerArrayList = null;


    public ViewHawkerActivity(){
        //Initalize List
        if(allHawkerArrayList == null){
            allHawkerArrayList = new ArrayList<>();
            DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("facilities");
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot childsnap : snapshot.getChildren()) {

                        //MUST CREATE FITNESS FIRST (NOW USING Facilities)

                        //Hawker
                        HawkerCentre hawkerCentre = childsnap.getValue(HawkerCentre.class);
                        allHawkerArrayList.add(hawkerCentre);
                        //Log.d("Ta444g",hawkerCentre.getImageUrl());
                    }
                    mDatabaseReference.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            //Load the food
            DatabaseReference mDatabaseReference2 = FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("foodItems");
            mDatabaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot childsnap : snapshot.getChildren()) {
                        //Create the food in the hawker
                        Food food = childsnap.getValue(Food.class);
                        for( HawkerCentre hawkerCentre : allHawkerArrayList){
                            //Check for the Hakwer
                            if(food.getNameOfHawker().equalsIgnoreCase(hawkerCentre.getName())){

                                if(hawkerCentre.getFoodList() == null){
                                    hawkerCentre.setFoodList(new ArrayList<>());
                                }
                                hawkerCentre.getFoodList().add(food);
                            }
                        }
                    }
                    mDatabaseReference2.removeEventListener(this);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_facilities);
        viewFacilitiesButton = (Button) findViewById(R.id.viewFacilitiesButton);
        viewFacilitiesImage = (ImageView) findViewById(R.id.viewFacilitiesImage);


        //Default fragment being contained is the facilityDisplay

        //If its a fitness facility, hide the button
        Bundle bundle = getIntent().getExtras();
        String nameOfMarkerClicked = bundle.getString("facilitiesName");


        Glide.with(this)
                .load(FacilityManager.getInstance().searchHawker(nameOfMarkerClicked).getImageUrl())
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

    public ArrayList<HawkerCentre> getAllHawkerArrayList() {
        return allHawkerArrayList;
    }

    public void setAllHawkerArrayList(ArrayList<HawkerCentre> allHawkerArrayList) {
        ViewHawkerActivity.allHawkerArrayList = allHawkerArrayList;
    }
}
