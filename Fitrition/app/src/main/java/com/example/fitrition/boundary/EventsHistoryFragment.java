package com.example.fitrition.boundary;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fitrition.R;
import com.example.fitrition.adapter.EventRecycleAdapter;
import com.example.fitrition.control.CalendarManager;
import com.example.fitrition.entities.Events;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


public class EventsHistoryFragment extends Fragment {

    View view;
    CalendarManager calendarManager;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    EventRecycleAdapter tracker_all_recycle_manager;

    ImageView backButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_events_history, container, false);

        calendarManager=CalendarManager.getInstance();
        initRecylerView(view);

        backButton=view.findViewById(R.id.tracker_all_back_icon);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,new TrackerFragment()).commit();
            }
        });

        return view;
    }

    private void initRecylerView(View view) {

        ArrayList<Events> eventsList = new ArrayList<Events>();
        ArrayList<Long> intList=new ArrayList<Long>();

        for (Events subject:calendarManager.getEventsList()){
            eventsList.add(subject);
            intList.add(Long.parseLong(subject.getYear()+subject.getMonth()+subject.getDate()+subject.getTime()));
        }
        Collections.sort(intList);
        Collections.reverse(intList);
        ArrayList<Events> sortedArray = new ArrayList<Events>();

        for (Long subject:intList){
            for (Events sub:eventsList){
                if(subject==Long.parseLong(sub.getYear()+sub.getMonth()+sub.getDate()+sub.getTime())){
                    sortedArray.add(sub);
                    eventsList.remove(sub);
                    break;
                }
            }
        }

        recyclerView=view.findViewById(R.id.tracker_all_recycle);
        layoutManager=new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        tracker_all_recycle_manager=new EventRecycleAdapter(sortedArray);
        recyclerView.setAdapter(tracker_all_recycle_manager);
    }
}