package com.example.fitrition.utils;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
        import android.app.DatePickerDialog;
        import android.content.Context;
import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fitrition.adapter.EventRecyclerAdapter;
import com.example.fitrition.boundary.DBOpenHelper;
import com.example.fitrition.entities.Events;
import com.example.fitrition.uiReference.tracker.ExpandableHeightGridView;
import com.example.fitrition.R;

import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;
        import java.util.Locale;

public class CalendarCustomView extends LinearLayout implements com.example.fitrition.utils.CalendarUtils {
    private static final String TAG = CalendarCustomView.class.getSimpleName();
    private ImageView previousButton, nextButton;
    private TextView currentDate;
    public ExpandableHeightGridView calendarGridView;
    private Button addEventButton;
    private Button saveEventButton;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private int month, year;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM, yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private Context context;
    private com.example.fitrition.utils.GridAdapter mAdapter;
    RecyclerView recyclerView;
    EventRecyclerAdapter eventRecyclerAdapter;
    List<Events> eventsList = new ArrayList<>();
    List<Date> dateList = new ArrayList<>();
    DBOpenHelper dbOpenHelper;
    AlertDialog alertDialog;

    public CalendarCustomView(Context context) {
        super(context);
        this.context = context;
        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setCurrentDateClickEvent();
        setAddEventButtonClickEvent();
//        dateClickEvent();
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


        //EVENTS RECYCLER ADAPTER TO BE DONE BY JEREMY
        RecyclerView EventRV= (RecyclerView) view.findViewById(R.id.my_feed);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        EventRV.setLayoutManager(layoutManager);
        EventRV.setHasFixedSize(true);

//        EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(view.getContext()
//                ,CollectEvent(date));
//        EventRV.setAdapter(eventRecyclerAdapter);
//        eventRecyclerAdapter.notifyDataSetChanged();
//        builder.setView(showView);
        AlertDialog.Builder builder =new AlertDialog.Builder(context);
        builder.setCancelable(true);
        alertDialog =builder.create();
        alertDialog.show();
    }

//    private ArrayList<Events> CollectEvent(String date) {
//        ArrayList<Events> arrayList = new ArrayList<>();
//        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
//        SQLiteDatabase sqLiteDatabase = dbOpenHelper.getReadableDatabase();
//        Cursor cursor = dbOpenHelper.ReadEvents(date, sqLiteDatabase);
//        while (cursor.moveToNext()) {
//            String event = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
//            String Time = cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
//            String Date = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
//            String month = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
//            String year = cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
//            Events events = new Events(event, Time, Date, month, year);
//            arrayList.add(events);
//        }
//        cursor.close();
//    }

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

    private void setCurrentDateClickEvent() {
        currentDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar currentDate = Calendar.getInstance();
                final Calendar date;
                date = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, final int dayOfMonth) {
                        cal.set(year, monthOfYear, dayOfMonth);
                        setUpCalendarAdapter();
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));
                datePickerDialog.show();
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

                saveEventButton = (Button) popupView.findViewById(R.id.buttonSaveEvent);
                TextView eventName = (TextView) popupView.findViewById(R.id.eventname);
                EditText eventTime = (EditText) popupView.findViewById(R.id.eventtime);
                EditText eventDate = (EditText) popupView.findViewById(R.id.eventdatebox);
                EditText eventMonth = (EditText) popupView.findViewById(R.id.eventmonthbox);
                EditText eventYear = (EditText) popupView.findViewById(R.id.eventyearbox);
                saveEventButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View popupView) {
                        //String eventNameStr = eventName.getText().toString();
                        SaveEvent(eventName.getText().toString(), eventTime.getText().toString()
                                , eventDate.getText().toString(), eventMonth.getText().toString()
                                , eventYear.getText().toString());
                        popupWindow.dismiss();
                   }
                });

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

    private void SaveEvent(String event,String time,String date, String month, String year){
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.SaveEvent(event,time,date,month,year,database);
        dbOpenHelper.close();
        Toast.makeText(context, "Event Saved", Toast.LENGTH_SHORT).show();
    }

//    private void dateClickEvent() {
//
//        calendarGridView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LayoutInflater inflater = (LayoutInflater)
//                        context.getSystemService(LAYOUT_INFLATER_SERVICE);
//                View popupView = inflater.inflate(R.layout.fragment_event_view, null);
//
//                // create the popup window
//                int width = LinearLayout.LayoutParams.MATCH_PARENT;
//                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                boolean focusable = true; // lets taps outside the popup also dismiss it
//                final PopupWindow popupWindow = new PopupWindow(popupView, width,  height, focusable);
//
//                // show the popup window
//                // which view you pass in doesn't matter, it is only used for the window tolken
//                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//
//                // dismiss the popup window when touched
//                popupView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        popupWindow.dismiss();
//                        return true;
//                    }
//                });
//            }
//        });
//
//        calendarGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                final String date = dateFormat.format(dateList.get(position));
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setCancelable(true);
//                View showView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_view,null);
//                RecyclerView EventRV= (RecyclerView) showView.findViewById(R.id.eventsRV);
//                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(showView.getContext());
//                EventRV.setLayoutManager(layoutManager);
//                EventRV.setHasFixedSize(true);
//
//                EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(showView.getContext()
//                        ,CollectEvent(date));
//                EventRV.setAdapter(eventRecyclerAdapter);
//                eventRecyclerAdapter.notifyDataSetChanged();
//                builder.setView(showView);
//                alertDialog =builder.create();
//                alertDialog.show();
//
//
//                return true;
//            }
//        });
//
//    }


    @Override
    public void nextMonth() {
        cal.add(Calendar.MONTH, 1);
        setUpCalendarAdapter();
    }

    @Override
    public void previousMonths() {
        cal.add(Calendar.MONTH, -1);
        setUpCalendarAdapter();
    }

}
