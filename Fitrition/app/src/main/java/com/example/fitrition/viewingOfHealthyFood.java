package com.example.fitrition;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fitrition.adapter.FriendListRecyclerAdapter;
import com.example.fitrition.adapter.StatusAdapter;
import com.example.fitrition.adapter.ViewingOfHealthyFoodAdapter;
import com.example.fitrition.control.FacilityManager;
import com.example.fitrition.entities.Food;
import com.example.fitrition.entities.Friend;
import com.example.fitrition.entities.Status;
import com.example.fitrition.utils.SpacingItemDecoration;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class viewingOfHealthyFood extends Fragment {

    RecyclerView recyclerView;
    ViewingOfHealthyFoodAdapter healthyFoodAdapter;
    List<Food> foodArrayList;
    List<Food> partialFoodArrayList;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void initData() {

        FacilityManager fm = FacilityManager.getInstance();

        foodArrayList = fm.getFoodList();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_viewing_of_healthy_food, container, false);

        Log.d("SucessFinalVIew", "No Exception"  );
        initData();

        //Here is where the list gets populate
        Bundle bundle = getActivity().getIntent().getExtras();
        String nameOfMarkerClicked = bundle.getString("facilitiesName");
        //Log.d("Ended",nameOfMarkerClicked);

        partialFoodArrayList = new ArrayList<>();
        for(Food f: foodArrayList ){
            if(f.getNameOfHawker().equalsIgnoreCase(nameOfMarkerClicked)){
                partialFoodArrayList.add(f);
            }
        }
        healthyFoodAdapter = new ViewingOfHealthyFoodAdapter(partialFoodArrayList);

        recyclerView= view.findViewById(R.id.viewingOfFoodRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(healthyFoodAdapter);

        return view;
    }


}
