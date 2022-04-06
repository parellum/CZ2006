package com.example.fitrition.utils;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.LinearLayout;
        import android.content.Context;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.example.fitrition.uiReference.tracker.ExpandableHeightGridView;
import com.example.fitrition.R;

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;
        import java.util.Locale;

public class CalendarCustomView extends LinearLayout {
    private static final String TAG = CalendarCustomView.class.getSimpleName();
    private ImageView previousButton, nextButton;
    private TextView currentDate;
    public ExpandableHeightGridView calendarGridView;
    private Button addEventButton;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM, yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private Context context;
    private com.example.fitrition.utils.GridAdapter mAdapter;


    public CalendarCustomView(Context context) {
        super(context);
        this.context = context;
        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setAddEventButtonClickEvent();;
    }


    private void initializeUILayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_calender, this);
        previousButton = (ImageView) view.findViewById(R.id.previous_month);
        nextButton = (ImageView) view.findViewById(R.id.next_month);
        currentDate = (TextView) view.findViewById(R.id.display_current_date);
        addEventButton = (Button) findViewById(R.id.buttonAddEvent);
        calendarGridView = (ExpandableHeightGridView) view.findViewById(R.id.calendar_grid);
        calendarGridView.setExpanded(true);
    }

    private void setPreviousButtonClickEvent() {
        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarCustomView.this.previousMonths();
            }
        });
    }

    private void setNextButtonClickEvent() {

        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarCustomView.this.nextMonth();
            }
        });
    }


    private void setAddEventButtonClickEvent() {
        addEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.fragment_add_event, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width,  height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });
    }


    public void setUpCalendarAdapter() {
        List<Date> dayValueInCells = new ArrayList<Date>();
        Calendar mCal = (Calendar) cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while (dayValueInCells.size() < MAX_CALENDAR_COLUMN) {
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TAG, "Number of date " + dayValueInCells.size());
        String sDate = formatter.format(cal.getTime());
        currentDate.setText(sDate);
        mAdapter = new com.example.fitrition.utils.GridAdapter(context, dayValueInCells, cal);
        calendarGridView.setAdapter(mAdapter);
    }

    public void nextMonth() {
        cal.add(Calendar.MONTH, 1);
        setUpCalendarAdapter();
    }

    public void previousMonths() {
        cal.add(Calendar.MONTH, -1);
        setUpCalendarAdapter();
    }

}
