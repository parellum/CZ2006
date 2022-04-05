package com.example.fitrition.boundary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.fitrition.R;

import java.util.Date;
import java.util.List;

public class NewEventFragment extends Fragment {

    List<Date> selectedDates;
    Date start, end;
    LinearLayout layoutCalender;
    View custom_view;
    Date initialDate, lastDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);
        return view;
    }



}