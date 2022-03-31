package com.example.fitrition.boundary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fitrition.R;
import com.example.fitrition.utils.CalendarCustomView;
import com.example.fitrition.utils.EventObjects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TrackerFragment extends Fragment {

    List<Date> selectedDates;
    Date start, end;
    LinearLayout layoutCalender;
    View custom_view;
    Date initialDate, lastDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);
        setInitializations(view);
        setCalenderView(view);
        return view;
    }

    private void setInitializations(View view) {
        custom_view = (View) view.findViewById(R.id.custom_view);
        layoutCalender = (LinearLayout) view.findViewById(R.id.layoutCalender);
    }

    public void setCalenderView(View view) {

        //Custom Events
        EventObjects eventObjects = new EventObjects(1, "Birth", new Date());
        eventObjects.setColor(R.color.teal_700);
        List<EventObjects> mEvents = new ArrayList<>();
        mEvents.add(eventObjects);

        ViewGroup parent = (ViewGroup) custom_view.getParent();
        parent.removeView(custom_view);
        layoutCalender.removeAllViews();
        layoutCalender.setOrientation(LinearLayout.VERTICAL);

        final CalendarCustomView calendarCustomView = new CalendarCustomView(view.getContext(), mEvents);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        calendarCustomView.setLayoutParams(layoutParams);
        layoutCalender.addView(calendarCustomView);

        calendarCustomView.calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getAdapter().getView((int) l, null, null).getAlpha() == 0.4f) {
                    Log.d("hello", "hello");
                } else {
                    Calendar today = Calendar.getInstance();
                    today.setTime(new Date());

                    Calendar tapedDay = Calendar.getInstance();
                    tapedDay.setTime((Date) adapterView.getAdapter().getItem((int) l));
                    boolean sameDay = tapedDay.get(Calendar.YEAR) == tapedDay.get(Calendar.YEAR) &&
                            today.get(Calendar.DAY_OF_YEAR) == tapedDay.get(Calendar.DAY_OF_YEAR);
                    if (today.after(tapedDay) && !sameDay) {
                        Toast.makeText(view.getContext(), "You can't select previous date.", Toast.LENGTH_LONG).show();
                    } else {
                        if (initialDate == null && lastDate == null) {
                            initialDate = lastDate = (Date) adapterView.getAdapter().getItem((int) l);
                        } else {
                            initialDate = lastDate;
                            lastDate = (Date) adapterView.getAdapter().getItem((int) l);
                        }
                        if (initialDate != null && lastDate != null)
                            calendarCustomView.setRangesOfDate(makeDateRanges());
                    }
                }
                try {
                    Toast.makeText(view.getContext(), "Start Date: " + initialDate.toString() + "\n End Date: " + lastDate.toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }
    public List<EventObjects> makeDateRanges() {
        if (lastDate.after(initialDate)) {
            start = initialDate;
            end = lastDate;
        } else {
            start = lastDate;
            end = initialDate;
        }
        List<EventObjects> eventObjectses = new ArrayList<>();
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.setTime(start);

        while (!gcal.getTime().after(end)) {
            Date d = gcal.getTime();
            EventObjects eventObject = new EventObjects("", d);
            eventObject.setColor(getResources().getColor(R.color.grey));
            eventObjectses.add(eventObject);
            gcal.add(Calendar.DATE, 1);
        }
        return eventObjectses;
    }
}