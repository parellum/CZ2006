package com.example.fitrition.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fitrition.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GridAdapter extends ArrayAdapter {
    private LayoutInflater mInflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;

    public GridAdapter( Context context, List<Date> monthlyDates, Calendar currentDate) {
        super(context, R.layout.fragment_single_cell);
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.fragment_single_cell, parent, false);
        }
        if (displayMonth == currentMonth && displayYear == currentYear) {
            view.setBackgroundColor(Color.parseColor("#F5F5F5"));
        } else {
            view.setAlpha(0.4f);
        }
        if (dayValue == currentDay && displayYear == 2022 && displayMonth == 4){
            view.setBackgroundColor(Color.parseColor("#428779"));
        }
        //Add day to calendar
        TextView cellNumber = (TextView) view.findViewById(R.id.calendar_date_id);
        cellNumber.setText(String.valueOf(dayValue));
        return view;
    }


    @Override
    public int getCount() {
        return monthlyDates.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyDates.get(position);
    }

    @Override
    public int getPosition(Object item) {
        return monthlyDates.indexOf(item);
    }
}