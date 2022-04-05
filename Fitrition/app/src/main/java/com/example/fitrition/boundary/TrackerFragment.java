package com.example.fitrition.boundary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.fitrition.R;
import com.example.fitrition.utils.CalendarCustomView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrackerFragment extends Fragment {

    List<Date> selectedDates;
    Date start, end;
    LinearLayout layoutCalender;
    View custom_view;
    Date initialDate, lastDate;
//    private Button addEventButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);
        setInitializations(view);
        setCalenderView(view);
//        setAddEventButtonClickEvent();
        return view;
    }

    private void setInitializations(View view) {
        custom_view = (View) view.findViewById(R.id.custom_view);
        layoutCalender = (LinearLayout) view.findViewById(R.id.layoutCalender);
//        addEventButton = (Button) view.findViewById(R.id.buttonAddEvent);

    }

    public void setCalenderView(View view) {

        ViewGroup parent = (ViewGroup) custom_view.getParent();
        parent.removeView(custom_view);
        layoutCalender.removeAllViews();
        layoutCalender.setOrientation(LinearLayout.VERTICAL);

        final CalendarCustomView calendarCustomView = new CalendarCustomView(view.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        calendarCustomView.setLayoutParams(layoutParams);
        layoutCalender.addView(calendarCustomView);


    }


//    private void setAddEventButtonClickEvent() {
//        addEventButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
////                startActivity(new Intent(MainActivity.this, newEventFragment.class));
//
//                Intent i = new Intent(getActivity(), NewEventFragment.class);
//                startActivity(i);
////                mAdapter = new com.example.fitrition.utils.GridAdapter(context, dayValueInCells, cal, eventObjects);
////                calendarGridView.setAdapter(mAdapter);
//
////                NewEventFragment fragment = new NewEventFragment();
////                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
////                transaction.replace(R.id.fragment_container_view_tag, fragment).commit();
//            }
//        });
//    }
}