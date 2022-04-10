package com.example.fitrition.boundary;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fitrition.R;
import com.example.fitrition.adapter.StatusAdapter;
import com.example.fitrition.control.FriendManager;
import com.example.fitrition.control.SocialManager;
import com.example.fitrition.entities.Friend;
import com.example.fitrition.entities.Status;
import com.example.fitrition.utils.SpacingItemDecoration;

import java.util.ArrayList;


public class SocialFragment extends Fragment {
    RecyclerView recyclerView;
    StatusAdapter statusAdapter;

    View view;


    SwipeRefreshLayout swipeRefreshLayout;

    private FriendManager friendManager;
    private ArrayList<Status> statusList;
    private ArrayList<String> friendIDList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_social, container, false);

        friendManager=FriendManager.getInstance();
        friendIDList=new ArrayList<String>();
        statusList=new ArrayList<Status>();
        for (Friend friendSub:friendManager.getFriendList()){
            friendIDList.add(friendSub.getUserID());
            for (Status statusSub:friendSub.getSocialStatus()){
                statusList.add(statusSub);
            }
        }
        statusAdapter = new StatusAdapter(statusList,friendIDList, friendManager.getFriendList());


        recyclerView = (RecyclerView)view.findViewById(R.id.social_feed);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SpacingItemDecoration itemDecoration = new SpacingItemDecoration(10);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(statusAdapter);

////        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(SocialFragment.this, DividerItemDecoration.VERTICAL);
////        recyclerView.addItemDecoration(dividerItemDecoration);
//
//
//
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                friendIDList=new ArrayList<String>();
                statusList=new ArrayList<Status>();
                for (Friend friendSub:friendManager.getFriendList()){
                    friendIDList.add(friendSub.getUserID());
                    for (Status statusSub:friendSub.getSocialStatus()){
                        statusList.add(statusSub);
                    }
                }
                statusAdapter.setData(statusList,friendIDList, friendManager.getFriendList());
                statusAdapter.notifyDataSetChanged();
                swipeRefreshLayout.bringToFront();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: HI");
    }

    @Override
    public void onResume() {
        super.onResume();
        friendIDList=new ArrayList<String>();
        statusList=new ArrayList<Status>();
        friendManager = FriendManager.getInstance();
        for (Friend friendSub:friendManager.getFriendList()){
            friendIDList.add(friendSub.getUserID());
        }
        SocialManager socialManager = SocialManager.getInstance();
        socialManager.loadFriendStatusList();
        statusAdapter.setData(socialManager.getFriendStatusList(),friendIDList, friendManager.getFriendList());
        statusAdapter.notifyDataSetChanged();
    }

}


