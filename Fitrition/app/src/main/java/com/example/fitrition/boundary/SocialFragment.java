package com.example.fitrition.boundary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitrition.R;
import com.example.fitrition.adapter.StatusAdapter;
import com.example.fitrition.entities.Status;
import com.example.fitrition.utils.SpacingItemDecoration;

import java.util.ArrayList;


public class SocialFragment extends Fragment {
    RecyclerView recyclerView;
    StatusAdapter statusAdapter;
    ArrayList<Status> statusList=new ArrayList<Status>();

    public static final String[] statusTest= {"Philip ate Chicken Rice at NTU Hall 15",
            "Mathew has achieved a hotstreak of 1000 days!",
            "Sam has completed her 9th Swimming session!",
            "Richard gave PastaExpress 5/5 stars!" ,
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again",
            "Philip ate Chicken Rice at NTU Hall 15 again"};
    //public static final int[] TvShowImgs = {R.drawable.breaking_bad_logo,R.drawable.rick_and_morty_logoo,R.drawable.friends,R.drawable.sherlock,R.drawable.stranger_things};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_social, container, false);
        for(int i=0;i<statusTest.length;i++)
        {
            Status status = new Status();

            status.setDescription(statusTest[i]);

            statusList.add(status);
        }


        statusAdapter = new StatusAdapter(statusList);

        recyclerView = (RecyclerView)view.findViewById(R.id.social_feed);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SpacingItemDecoration itemDecoration = new SpacingItemDecoration(10);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(statusAdapter);

        return view;
    }
}