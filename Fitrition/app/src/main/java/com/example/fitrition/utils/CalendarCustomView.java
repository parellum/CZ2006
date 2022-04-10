package com.example.fitrition.utils;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
        import android.content.Context;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

import com.example.fitrition.control.CalendarManager;
import com.example.fitrition.entities.Events;
import com.example.fitrition.uiReference.tracker.ExpandableHeightGridView;
import com.example.fitrition.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;
        import java.util.Locale;
import java.util.TimeZone;

public class CalendarCustomView extends LinearLayout {
    private static final String TAG = CalendarCustomView.class.getSimpleName();
    private ImageView previousButton, nextButton;
    private TextView currentDate;
    public ExpandableHeightGridView calendarGridView;
    private Button addEventButton;

    private static final int MAX_CALENDAR_COLUMN = 42;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM, yyyy", Locale.ENGLISH);
    private Calendar cal;
    private Context context;
    private com.example.fitrition.utils.GridAdapter mAdapter;
    private CalendarManager calendarManager;

    ArrayList<Events> arrayList;

    DatabaseReference mDatabaseReference;
    FirebaseAuth mAuth;


    public CalendarCustomView(Context context) {
        super(context);
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Singapore"));
        cal=Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"));

        this.context = context;
        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setAddEventButtonClickEvent();
        setAllEventButtonClickEvent();
    }

    private void initializeUILayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_calender, this);
        previousButton = (ImageView) view.findViewById(R.id.previous_month);
        nextButton = (ImageView) view.findViewById(R.id.next_month);
        currentDate = (TextView) view.findViewById(R.id.display_current_date);
        addEventButton = (Button) findViewById(R.id.buttonAddEvent);
        allEventButton = (Button) findViewById(R.id.buttonAllEvent);
        calendarGridView = (ExpandableHeightGridView) view.findViewById(R.id.calendar_grid);
        calendarGridView.setExpanded(true);
        mAuth=FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("events").child(mAuth.getCurrentUser().getUid());
        calendarManager=CalendarManager.getInstance();
        arrayList = calendarManager.getEventsList();
    }

    private void setPreviousButtonClickEvent() {
        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarCustomView.this.previousMonths();
            }
        });
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


                TextView eventName = (TextView) popupView.findViewById(R.id.eventname);
                TextView eventLocation = (TextView) popupView.findViewById(R.id.eventlocation);
                EditText eventTime = (EditText) popupView.findViewById(R.id.eventtime);
                EditText eventDate = (EditText) popupView.findViewById(R.id.eventdatebox);
                EditText eventMonth = (EditText) popupView.findViewById(R.id.eventmonthbox);
                EditText eventYear = (EditText) popupView.findViewById(R.id.eventyearbox);
                Button saveEventButton = (Button) popupView.findViewById(R.id.buttonSaveEvent);
                saveEventButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View popupView) {
                        String err_msg = "";
                        if (eventName.getText().toString() == "") {
                            err_msg = err_msg + "Name is not valid. Please enter the event name.\n";
                        }
                        if (eventLocation.getText().toString() == "") {
                            err_msg = err_msg + "Location is not valid. Please enter a valid location.\n";
                        }
                        int time_int = Integer.parseInt(eventTime.getText().toString());
                        if (time_int < 0 || time_int >2359){
                            err_msg = err_msg + "Time is not valid. Please enter number in range of 0000 to 2359\n";
                        }
                        int date_int = Integer.parseInt(eventDate.getText().toString());
                        if (date_int < 1 || date_int >31){
                            err_msg = err_msg + "Date is not valid. Please enter number in range of 1 to 31\n";
                        }
                        int month_int = Integer.parseInt(eventMonth.getText().toString());
                        if (month_int < 1 || month_int >12){
                            err_msg = err_msg + "Month is not valid. Please enter number in range of 1 to 12\n";
                        }
                        int year_int = Integer.parseInt(eventYear.getText().toString());
                        if (year_int < 1900 || year_int >2100){
                            err_msg = err_msg + "Year is not valid. Please enter number in range of 1900 to 2100\n";
                        }
                        if (err_msg == "") {
                            SaveEvent(eventName.getText().toString(), eventLocation.getText().toString()
                                    , eventTime.getText().toString()
                                    , eventDate.getText().toString(), eventMonth.getText().toString()
                                    , eventYear.getText().toString());
                            popupWindow.dismiss();
                        }
                        else {
                            Toast.makeText(context, err_msg, Toast.LENGTH_LONG).show();
                        }
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

    private void SaveEvent(String event, String location,String time,String date, String month, String year){
        Events events = new Events(event,location,time,date,month,year);
        mDatabaseReference.child(Long.toString(Calendar.getInstance().getTimeInMillis())).setValue(events).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Successfully Saved", Toast.LENGTH_SHORT).show();
            }
        });
        arrayList.add(events);
    }

    private void EditEvent(String event, String location,String time,String date, String month, String year, int pos){
        Events events = new Events(event,location,time,date,month,year);
        mDatabaseReference.child(Long.toString(Calendar.getInstance().getTimeInMillis())).setValue(events).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Successfully Saved", Toast.LENGTH_SHORT).show();
            }
        });
        arrayList.set(pos, events);
    }

    public void dateClickEvent(View view, Date date) {
        LayoutInflater inflater = (LayoutInflater)
                view.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fragment_add_event, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width,  height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        EditText eventDate = (EditText) popupView.findViewById(R.id.eventdatebox);
        EditText eventMonth = (EditText) popupView.findViewById(R.id.eventmonthbox);
        EditText eventYear = (EditText) popupView.findViewById(R.id.eventyearbox);
        SimpleDateFormat dateFormatDay= new SimpleDateFormat("dd");
        String forDay = dateFormatDay.format(date);
        SimpleDateFormat dateFormatMonth= new SimpleDateFormat("MM");
        String forMonth = dateFormatMonth.format(date);
        SimpleDateFormat dateFormatYear= new SimpleDateFormat("yyyy");
        String forYear = dateFormatYear.format(date);
        eventDate.setText(forDay);
        eventMonth.setText(forMonth);
        eventYear.setText(forYear);


        Button saveEventButton = (Button) popupView.findViewById(R.id.buttonSaveEvent);
        TextView eventName = (TextView) popupView.findViewById(R.id.eventname);
        TextView eventLocation = (TextView) popupView.findViewById(R.id.eventlocation);
        EditText eventTime = (EditText) popupView.findViewById(R.id.eventtime);

        saveEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View popupView) {
                String err_msg = "";
                if (eventName.getText().toString() == "") {
                    err_msg = err_msg + "Name is not valid. Please enter the event name.\n";
                }
                if (eventLocation.getText().toString() == "") {
                    err_msg = err_msg + "Location is not valid. Please enter a valid location.\n";
                }
                int time_int = Integer.parseInt(eventTime.getText().toString());
                if (time_int < 0 || time_int >2359){
                    err_msg = err_msg + "Time is not valid. Please enter number in range of 0000 to 2359\n";
                }
                int date_int = Integer.parseInt(eventDate.getText().toString());
                if (date_int < 1 || date_int >31){
                    err_msg = err_msg + "Date is not valid. Please enter number in range of 1 to 31\n";
                }
                int month_int = Integer.parseInt(eventMonth.getText().toString());
                if (month_int < 1 || month_int >12){
                    err_msg = err_msg + "Month is not valid. Please enter number in range of 1 to 12\n";
                }
                int year_int = Integer.parseInt(eventYear.getText().toString());
                if (year_int < 1900 || year_int >2100){
                    err_msg = err_msg + "Year is not valid. Please enter number in range of 1900 to 2100\n";
                }
                if (err_msg == "") {
                    SaveEvent(eventName.getText().toString(), eventLocation.getText().toString()
                            , eventTime.getText().toString()
                            , eventDate.getText().toString(), eventMonth.getText().toString()
                            , eventYear.getText().toString());
                    popupWindow.dismiss();
                }
                else {
                    Toast.makeText(context, err_msg, Toast.LENGTH_LONG).show();
                }
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



    private void setAllEventButtonClickEvent() {
        allEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                array_point = 0;
                previous_page_array_point = 0;
                next_page_array_point = 0;
                inflateEventList(view);
            }
        });
    }

    private void inflateEventList(View view){
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fragment_event_list, null);
        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width,  height, focusable);
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // Add event popup
                TextView eventName = (TextView) popupView.findViewById(R.id.eventname);
        TextView eventLocation = (TextView) popupView.findViewById(R.id.eventlocation);
        EditText eventTime = (EditText) popupView.findViewById(R.id.eventtime);
        EditText eventDate = (EditText) popupView.findViewById(R.id.eventdatebox);
        EditText eventMonth = (EditText) popupView.findViewById(R.id.eventmonthbox);
        EditText eventYear = (EditText) popupView.findViewById(R.id.eventyearbox);
        Button saveEventButton = (Button) popupView.findViewById(R.id.buttonSaveEvent);

        eventCellTV1 = popupView.findViewById(R.id.eventListTV1);
        eventCellTV2 = popupView.findViewById(R.id.eventListTV2);
        eventCellTV3 = popupView.findViewById(R.id.eventListTV3);
        eventCellTV4 = popupView.findViewById(R.id.eventListTV4);
        next_page_array_point = eventListViewer(popupView, array_point);

        Button buttonDeleteTV1 = (Button) popupView.findViewById(R.id.buttonDeleteTV1);
        buttonDeleteTV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View popupView) {
                arrayList.remove(array_point1);
                String text = "Event removed successfully";
                eventCellTV1.setText(text);
            }
        });

        Button buttonEditTV1 = (Button) popupView.findViewById(R.id.buttonEditTV1);
        buttonEditTV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View popupView) {
                popupWindow.dismiss();
                eventListEditor(view, array_point1);
            }
        });

        Button buttonDeleteTV2 = (Button) popupView.findViewById(R.id.buttonDeleteTV2);

        buttonDeleteTV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View popupView) {
                arrayList.remove(array_point2);
                String text = "Event removed successfully";
                eventCellTV2.setText(text);
            }
        });

        Button buttonEditTV2 = (Button) popupView.findViewById(R.id.buttonEditTV2);
        buttonEditTV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View popupView) {
                popupWindow.dismiss();
                eventListEditor(view, array_point2);
            }
        });

        Button buttonDeleteTV3 = (Button) popupView.findViewById(R.id.buttonDeleteTV3);
        buttonDeleteTV3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View popupView) {
                arrayList.remove(array_point3);
                String text = "Event removed successfully";
                eventCellTV3.setText(text);
            }
        });

        Button buttonEditTV3 = (Button) popupView.findViewById(R.id.buttonEditTV3);
        buttonEditTV3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View popupView) {
                popupWindow.dismiss();
                eventListEditor(view, array_point3);
            }
        });

        Button buttonDeleteTV4 = (Button) popupView.findViewById(R.id.buttonDeleteTV4);
        buttonDeleteTV4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View popupView) {
                arrayList.remove(array_point4);
                String text = "Event removed successfully";
                eventCellTV4.setText(text);
            }
        });

        Button buttonEditTV4 = (Button) popupView.findViewById(R.id.buttonEditTV4);
        buttonEditTV4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View popupView) {
                popupWindow.dismiss();
                eventListEditor(view, array_point4);
            }
        });

        Button buttonPreviousPage = (Button) popupView.findViewById(R.id.buttonPreviousPage);
        buttonPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View popupView) {
                next_page_array_point = eventListViewer(popupView, previous_page_array_point);
                previous_page_array_point = 0;
            }
        });

        Button buttonNextPage = (Button) popupView.findViewById(R.id.buttonNextPage);
        buttonNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View popupView) {
                previous_page_array_point = array_point;
                array_point = next_page_array_point;
                next_page_array_point = eventListViewer(popupView, next_page_array_point);

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


    private int eventListViewer(View popupView, int array_point){
        Events events;
        String text;

        for (int i = array_point; i < arrayList.size(); i +=1){
            if(i == arrayList.size() - 1) {
                eventCellTV2.setText("No more events this month");
                eventCellTV3.setText("No more events this month");
                eventCellTV4.setText("No more events this month");
            }
            events = arrayList.get(i);
            //Toast.makeText(context, "Total Number of Events this month:" + String.format("%d", arrayList.size()), Toast.LENGTH_LONG).show();
            if ((cal.get(Calendar.MONTH) + 1) == Integer.parseInt(events.getMonth())){
                array_point1 = i;
                text = "";
                text = text + "\nEvent Name: " + events.getEvent() + "\nEvent Location: " + events.getLocation()
                        + "\nEvent Time: " + events.getTime() + "\nEvent Date: " + events.getDate() + " " + events.getMonth()
                        + " " + events.getYear();
                eventCellTV1.setText(text);
                array_point +=1;
                break;
            }
            array_point +=1;
        }

        for (int i = array_point; i < arrayList.size(); i +=1){if(i == arrayList.size() - 1) {
            eventCellTV3.setText("No more events this month");
            eventCellTV4.setText("No more events this month");
        }

            events = arrayList.get(i);
            //Toast.makeText(context, cal.get(Calendar.YEAR), Toast.LENGTH_LONG).show();
            if ((cal.get(Calendar.MONTH) + 1) == Integer.parseInt(events.getMonth())){
                array_point2 = i;
                text = "";
                text = text + "\nEvent Name: " + events.getEvent() + "\nEvent Location: " + events.getLocation()
                        + "\nEvent Time: " + events.getTime() + "\nEvent Date: " + events.getDate() + " " + events.getMonth()
                        + " " + events.getYear();
                eventCellTV2.setText(text);
                array_point +=1;
                break;
            }
            array_point +=1;
        }

        for (int i = array_point; i < arrayList.size(); i +=1){
            if(i == arrayList.size() - 1) {
                eventCellTV4.setText("No more events this month");
            }
            array_point3 = i;
            events = arrayList.get(i);
            //Toast.makeText(context, cal.get(Calendar.YEAR), Toast.LENGTH_LONG).show();
            if ((cal.get(Calendar.MONTH) + 1) == Integer.parseInt(events.getMonth())){
                text = "";
                text = text + "\nEvent Name: " + events.getEvent() + "\nEvent Location: " + events.getLocation()
                        + "\nEvent Time: " + events.getTime() + "\nEvent Date: " + events.getDate() + " " + events.getMonth()
                        + " " + events.getYear();
                eventCellTV3.setText(text);
                array_point +=1;
                break;
            }
            array_point +=1;
        }

        for (int i = array_point; i < arrayList.size(); i +=1){
            array_point4 = i;
            events = arrayList.get(i);
            //Toast.makeText(context, cal.get(Calendar.YEAR), Toast.LENGTH_LONG).show();
            if ((cal.get(Calendar.MONTH) + 1) == Integer.parseInt(events.getMonth())){
                text = "";
                text = text + "\nEvent Name: " + events.getEvent() + "\nEvent Location: " + events.getLocation()
                        + "\nEvent Time: " + events.getTime() + "\nEvent Date: " + events.getDate() + " " + events.getMonth()
                        + " " + events.getYear();
                eventCellTV4.setText(text);
                array_point +=1;
                break;
            }
            array_point +=1;
        }
        return array_point;
    }

    public void eventListEditor(View view, int pos) {
        LayoutInflater inflater = (LayoutInflater)
                view.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fragment_add_event, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width,  height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        EditText eventDate = (EditText) popupView.findViewById(R.id.eventdatebox);
        EditText eventMonth = (EditText) popupView.findViewById(R.id.eventmonthbox);
        EditText eventYear = (EditText) popupView.findViewById(R.id.eventyearbox);

        Button saveEventButton = (Button) popupView.findViewById(R.id.buttonSaveEvent);
        TextView eventName = (TextView) popupView.findViewById(R.id.eventname);
        TextView eventLocation = (TextView) popupView.findViewById(R.id.eventlocation);
        EditText eventTime = (EditText) popupView.findViewById(R.id.eventtime);
        Events events = arrayList.get(pos);
        eventName.setText(events.getEvent());
        eventLocation.setText(events.getLocation());
        eventTime.setText(events.getTime());
        eventDate.setText(events.getDate());
        eventMonth.setText(events.getMonth());
        eventYear.setText(events.getYear());

        saveEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View popupView) {
                String err_msg = "";
                if (eventName.getText().toString() == "") {
                    err_msg = err_msg + "Name is not valid. Please enter the event name.\n";
                }
                if (eventLocation.getText().toString() == "") {
                    err_msg = err_msg + "Location is not valid. Please enter a valid location.\n";
                }
                int time_int = Integer.parseInt(eventTime.getText().toString());
                if (time_int < 0 || time_int >2359){
                    err_msg = err_msg + "Time is not valid. Please enter number in range of 0000 to 2359\n";
                }
                int date_int = Integer.parseInt(eventDate.getText().toString());
                if (date_int < 1 || date_int >31){
                    err_msg = err_msg + "Date is not valid. Please enter number in range of 1 to 31\n";
                }
                int month_int = Integer.parseInt(eventMonth.getText().toString());
                if (month_int < 1 || month_int >12){
                    err_msg = err_msg + "Month is not valid. Please enter number in range of 1 to 12\n";
                }
                int year_int = Integer.parseInt(eventYear.getText().toString());
                if (year_int < 1900 || year_int >2100){
                    err_msg = err_msg + "Year is not valid. Please enter number in range of 1900 to 2100\n";
                }
                if (err_msg == "") {
                    EditEvent(eventName.getText().toString(), eventLocation.getText().toString()
                            , eventTime.getText().toString()
                            , eventDate.getText().toString(), eventMonth.getText().toString()
                            , eventYear.getText().toString(), pos);
                    popupWindow.dismiss();
                }
                else {
                    Toast.makeText(context, err_msg, Toast.LENGTH_LONG).show();
                }
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
    private void initializeUILayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_calender, this);
        previousButton = (ImageView) view.findViewById(R.id.previous_month);
        nextButton = (ImageView) view.findViewById(R.id.next_month);
        currentDate = (TextView) view.findViewById(R.id.display_current_date);
        addEventButton = (Button) findViewById(R.id.buttonAddEvent);
        calendarGridView = (ExpandableHeightGridView) view.findViewById(R.id.calendar_grid);
        calendarGridView.setExpanded(true);
        mAuth=FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("events").child(mAuth.getCurrentUser().getUid());
        calendarManager=CalendarManager.getInstance();
        arrayList = calendarManager.getEventsList();
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
