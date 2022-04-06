package com.example.fitrition.boundary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fitrition.R;
import com.example.fitrition.adapter.StatusAdapter;
import com.example.fitrition.entities.Status;
import com.example.fitrition.utils.SpacingItemDecoration;

import java.util.ArrayList;


public class SocialFragment extends Fragment {
    RecyclerView recyclerView;
    StatusAdapter statusAdapter;
    ArrayList<Status> statusList=new ArrayList<Status>();


    SwipeRefreshLayout swipeRefreshLayout;

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
            };
    //public static final int[] TvShowImgs = {R.drawable.breaking_bad_logo,R.drawable.rick_and_morty_logoo,R.drawable.friends,R.drawable.sherlock,R.drawable.stranger_things};


    public static final String[] statusTest2 = {"Adam ate Chicken Rice at NTU Hall 15",
            "Zac has achieved a hotstreak of 1000 days!",
            "Lilian has completed her 9th Swimming session!",
            "Janet gave PastaExpress 5/5 stars!" ,
            "Eve ate Chicken Rice at NTU Hall 15 again"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_social, container, false);

        for(int i=0;i<statusTest.length;i++) //set a limit to the number of status shown
        {
            Status status = new Status();

            status.setDescription(statusTest[i]);

            statusList.add(status);

            if (statusList.size()>20){
                statusList.remove(statusList.size() - 1);
            }
        }


        statusAdapter = new StatusAdapter(statusList);

        recyclerView = (RecyclerView)view.findViewById(R.id.social_feed);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SpacingItemDecoration itemDecoration = new SpacingItemDecoration(10);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(statusAdapter);

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(SocialFragment.this, DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);



        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                for(int i=0;i<statusTest2.length;i++) //set a limit to the number of status shown
                {
                    Status status = new Status();

                    status.setDescription(statusTest2[i]);

                    statusList.add(status);
                    statusList.add(0, status);

                    if (statusList.size()>20){
                        statusList.remove(statusList.size() - 1);
                    }

                }

                statusAdapter.notifyDataSetChanged();
                swipeRefreshLayout.bringToFront();
                swipeRefreshLayout.setRefreshing(false);



            }
        });

        return view;
    }

}

