package com.example.fitrition.boundary;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitrition.R;
import com.example.fitrition.adapter.StatusAdapter;
import com.example.fitrition.adapter.StatusUserFocusAdapter;
import com.example.fitrition.control.ProfileManager;
import com.example.fitrition.control.SocialManager;
import com.example.fitrition.entities.Status;
import com.example.fitrition.utils.SpacingItemDecoration;

import java.util.ArrayList;

public class StatusHistoryFragment extends Fragment {
    RecyclerView recyclerView;
    StatusUserFocusAdapter statusUserFocusAdapter;
    ArrayList<Status> statusList;
    private SocialManager socialManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_status_history, container, false);
        socialManager=SocialManager.getInstance();
        socialManager.loadPersonalStatusList();
        statusList=socialManager.getPersonalStatusList();
        statusUserFocusAdapter = new StatusUserFocusAdapter(statusList);
        recyclerView = (RecyclerView)view.findViewById(R.id.status_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SpacingItemDecoration itemDecoration = new SpacingItemDecoration(10);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(statusUserFocusAdapter);

        return view;
    }
}