package com.example.fitrition;

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
import com.example.fitrition.entities.Food;
import com.example.fitrition.entities.Friend;
import com.example.fitrition.entities.Status;
import com.example.fitrition.utils.SpacingItemDecoration;
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

        try {
            Gson gson;
            gson = new GsonBuilder().create();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getActivity().getApplicationContext().getAssets().open("food.txt")));

            foodArrayList = gson.fromJson(reader, new TypeToken<List<Food>>(){}.getType());
//            FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("facilities").setValue(foodList);

             Log.d("SucessFinalVIew", "what Inner" + Integer.toString(foodArrayList.size()));
        }
        catch(Exception e) {
            Log.d("FailureFinalVIew", "Exception"  );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_viewing_of_healthy_food, container, false);

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
