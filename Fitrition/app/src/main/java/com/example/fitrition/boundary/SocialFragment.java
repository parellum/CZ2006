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
import com.example.fitrition.entities.Friend;
import com.example.fitrition.entities.Status;
import com.example.fitrition.utils.SpacingItemDecoration;

import java.util.ArrayList;


public class SocialFragment extends Fragment {
    RecyclerView recyclerView;
    StatusAdapter statusAdapter;


    SwipeRefreshLayout swipeRefreshLayout;

    private FriendManager friendManager;
    private ArrayList<Status> statusList;
    private ArrayList<String> friendIDList;

    public static final String[] statusTest2 = {"Adam ate Chicken Rice at NTU Hall 15",
            "Zac has achieved a hotstreak of 1000 days!",
            "Lilian has completed her 9th Swimming session!",
            "Janet gave PastaExpress 5/5 stars!" ,
            "Eve ate Chicken Rice at NTU Hall 15 again"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_social, container, false);

        friendManager=FriendManager.getInstance();
        friendIDList=new ArrayList<String>();
        statusList=new ArrayList<Status>();
        Log.d(TAG, "onCreateView: hi");
        for (Friend friendSub:friendManager.getFriendList()){
            friendIDList.add(friendSub.getUserID());
            for (Status statusSub:friendSub.getSocialStatus()){
                statusList.add(statusSub);
            }
        }
        Log.d(TAG, "onCreateView: "+statusList.size());
        Log.d(TAG, "onCreateView: "+friendIDList.size());
        Log.d(TAG, "onCreateView: "+friendManager.getFriendList().size());
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
//        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//
//                for(int i=0;i<statusTest2.length;i++) //set a limit to the number of status shown
//                {
//                    Status status = new Status();
//
//                    status.setDescription(statusTest2[i]);
//
//                    statusList.add(status);
//                    statusList.add(0, status);
//
//                    if (statusList.size()>20){
//                        statusList.remove(statusList.size() - 1);
//                    }
//
//                }
//
//                statusAdapter.notifyDataSetChanged();
//                swipeRefreshLayout.bringToFront();
//                swipeRefreshLayout.setRefreshing(false);
//
//
//
//            }
//        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getParentFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        getParentFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}


