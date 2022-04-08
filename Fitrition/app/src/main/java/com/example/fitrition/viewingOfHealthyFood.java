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

import java.util.ArrayList;
import java.util.List;

public class viewingOfHealthyFood extends Fragment {

    RecyclerView recyclerView;
    ViewingOfHealthyFoodAdapter healthyFoodAdapter;
    List<Food> foodList;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void initData() {
        foodList =  new ArrayList<>();
        foodList.add(new Food(1,"Chicken Rice","Stall 1", "Place Holder Description",12.34,"nill"));
        foodList.add(new Food(2,"Chicken Noodle","Stall 2", "Place Holder Description",56.78,"nill"));
        foodList.add(new Food(3,"Chicken Soup","Stall 3", "Place Holder Description",91.23,"nill"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_viewing_of_healthy_food, container, false);

        initData();

        healthyFoodAdapter = new ViewingOfHealthyFoodAdapter(foodList);

        recyclerView= view.findViewById(R.id.viewingOfFoodRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(healthyFoodAdapter);

        return view;
    }


}
