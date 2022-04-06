package com.example.fitrition.boundary;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fitrition.R;
import com.example.fitrition.utils.CalendarCustomView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TrackerFragment extends Fragment {

    LinearLayout layoutCalender;
    View custom_view;
    View calendar_date_id;
    View viewCopy;
    Date date;
    int n = 0;
    int colorId;
//    private Button addEventButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        final CalendarCustomView calendarCustomView = new CalendarCustomView(view.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        calendarCustomView.setLayoutParams(layoutParams);
        layoutCalender.addView(calendarCustomView);

        calendarCustomView.calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(n == 1) {
                    if (colorId == -657931)
                        viewCopy.setBackgroundColor(Color.parseColor("#F5F5F5"));
                    else if (colorId == -12417159)
                        viewCopy.setBackgroundColor(Color.parseColor("#428779"));
                }
                Calendar today = Calendar.getInstance();
                today.setTime(new java.util.Date());

                Calendar tapedDay = Calendar.getInstance();
                tapedDay.setTime((Date) adapterView.getAdapter().getItem((int) l));
                date = (Date) adapterView.getAdapter().getItem((int) l);

                SimpleDateFormat dateFormat= new SimpleDateFormat("dd MMMM yyyy");
                String dateOnly = dateFormat.format(date);

                //Toast.makeText(TrackerFragment.this, "Date: " + dateOnly, Toast.LENGTH_LONG).show();


                colorId = ((ColorDrawable) view.getBackground()).getColor();
                view.setBackgroundColor(Color.parseColor("#326e62"));
                viewCopy = view;
                n = 1;
            }
        });

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