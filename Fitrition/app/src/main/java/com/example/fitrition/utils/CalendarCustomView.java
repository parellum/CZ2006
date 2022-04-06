package com.example.fitrition.utils;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

<<<<<<< Updated upstream
import android.content.Intent;
=======
import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.media.metrics.Event;
>>>>>>> Stashed changes
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.LinearLayout;
        import android.app.DatePickerDialog;
        import android.content.Context;
        import android.util.AttributeSet;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
<<<<<<< Updated upstream

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;



import com.example.fitrition.MainActivity;
import com.example.fitrition.boundary.NewEventFragment;
=======
import android.widget.Toast;
import android.database.Cursor;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fitrition.adapter.EventRecyclerAdapter;
import com.example.fitrition.adapter.StatusAdapter;
import com.example.fitrition.boundary.DBOpenHelper;
import com.example.fitrition.boundary.DBStructure;
import com.example.fitrition.entities.Events;
import com.example.fitrition.entities.Status;
>>>>>>> Stashed changes
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
    private int month, year;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM, yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private Context context;
    private com.example.fitrition.utils.GridAdapter mAdapter;
<<<<<<< Updated upstream

=======
    RecyclerView recyclerView;
    EventRecyclerAdapter eventRecyclerAdapter;
    ArrayList<Events> eventsList = new ArrayList<>();
    List<Date> dateList = new ArrayList<>();
    DBOpenHelper dbOpenHelper;
    AlertDialog alertDialog;
>>>>>>> Stashed changes

    public CalendarCustomView(Context context) {
        super(context);
        this.context = context;
        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setAddEventButtonClickEvent();;
    }

//    public static final String[] eventTest= {"Philip ate Chicken Rice at NTU Hall 15",
//            "Mathew has achieved a hotstreak of 1000 days!",
//            "Sam has completed her 9th Swimming session!",
//            "Richard gave PastaExpress 5/5 stars!" ,
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again",
//            "Philip ate Chicken Rice at NTU Hall 15 again"}


    private void initializeUILayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_calender, this);
        previousButton = (ImageView) view.findViewById(R.id.previous_month);
        nextButton = (ImageView) view.findViewById(R.id.next_month);
        currentDate = (TextView) view.findViewById(R.id.display_current_date);
        addEventButton = (Button) findViewById(R.id.buttonAddEvent);
        calendarGridView = (ExpandableHeightGridView) view.findViewById(R.id.calendar_grid);
        calendarGridView.setExpanded(true);
<<<<<<< Updated upstream
    }

=======


        //EVENTS RECYCLER ADAPTER TO BE DONE BY JEREMY
//        Events event1 = new Events("Presentation", "1030", "11", "04", "22");

        RecyclerView EventRV= (RecyclerView) view.findViewById(R.id.my_feed);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        EventRV.setLayoutManager(layoutManager);
        EventRV.setHasFixedSize(true);

        EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(view.getContext()
                , eventsList);
        EventRV.setAdapter(eventRecyclerAdapter);
        eventRecyclerAdapter.notifyDataSetChanged();

        AlertDialog.Builder builder =new AlertDialog.Builder(context);
        builder.setCancelable(true);
        alertDialog =builder.create();
        alertDialog.show();
    }

    private ArrayList<Events> CollectEvent(String date){
        ArrayList<Events> arrayList = new ArrayList<>();
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadEvents(date,sqLiteDatabase);
        while (cursor.moveToNext()){
            String event = cursor.getString(1);
            String Time = cursor.getString(2);
            String Date = cursor.getString(3);
            String month = cursor.getString(4);
            String year = cursor.getString(5);
            Events events = new Events(event,Time,Date,month,year);
            arrayList.add(events);
        }
        cursor.close();
        dbOpenHelper.close();
// Toast.makeText(context, String.valueOf(arrayList.size()), Toast.LENGTH_SHORT).show();

        return arrayList;
    }

>>>>>>> Stashed changes
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
