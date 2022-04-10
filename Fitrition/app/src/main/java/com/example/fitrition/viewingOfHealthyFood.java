package com.example.fitrition;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitrition.adapter.ViewingOfHealthyFoodAdapter;
import com.example.fitrition.control.FacilityManager;
import com.example.fitrition.entities.Food;

import java.util.ArrayList;

public class viewingOfHealthyFood extends Fragment {

    RecyclerView recyclerView;
    ViewingOfHealthyFoodAdapter healthyFoodAdapter;
    //List<Food> foodArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /*private void initData() {

        FacilityManager fm = FacilityManager.getInstance();

        foodArrayList = fm.getFoodList();


    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_viewing_of_healthy_food, container, false);


        //initData();

        //Here is where the list gets populate
        Bundle bundle = getActivity().getIntent().getExtras();
        String nameOfMarkerClicked = bundle.getString("facilitiesName");
        ArrayList<Food> partialFoodArrayList = FacilityManager.getInstance().searchForFoodOfferedByHawker(nameOfMarkerClicked);

        if(partialFoodArrayList == null)
            healthyFoodAdapter = new ViewingOfHealthyFoodAdapter(new ArrayList<>());
        else
            healthyFoodAdapter = new ViewingOfHealthyFoodAdapter(partialFoodArrayList);

        recyclerView= view.findViewById(R.id.viewingOfFoodRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(healthyFoodAdapter);

        return view;
    }


}
