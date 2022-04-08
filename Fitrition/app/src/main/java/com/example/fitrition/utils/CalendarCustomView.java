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

public class CalendarCustomView extends LinearLayout {
    private static final String TAG = CalendarCustomView.class.getSimpleName();
    private ImageView previousButton, nextButton;
    private TextView currentDate;
    public ExpandableHeightGridView calendarGridView;
    private Button addEventButton;
    private Button allEventButton;

    private static final int MAX_CALENDAR_COLUMN = 42;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM, yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private Context context;
    private com.example.fitrition.utils.GridAdapter mAdapter;
    private DatePicker datePicker;
    private CalendarManager calendarManager;

    ArrayList<Events> arrayList;
    TextView eventCellTV;

    DatabaseReference mDatabaseReference;
    FirebaseAuth mAuth;


    public CalendarCustomView(Context context) {
        super(context);
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

                Button saveEventButton = (Button) popupView.findViewById(R.id.buttonSaveEvent);
                TextView eventName = (TextView) popupView.findViewById(R.id.eventname);
                TextView eventLocation = (TextView) popupView.findViewById(R.id.eventlocation);
                EditText eventTime = (EditText) popupView.findViewById(R.id.eventtime);
                EditText eventDate = (EditText) popupView.findViewById(R.id.eventdatebox);
                EditText eventMonth = (EditText) popupView.findViewById(R.id.eventmonthbox);
                EditText eventYear = (EditText) popupView.findViewById(R.id.eventyearbox);
                saveEventButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View popupView) {
                        String err_msg = "";
                        int year_int = Integer.parseInt(eventYear.getText().toString());
                        if (year_int < 1950 || year_int >2100){
                            err_msg = err_msg + "Year is not valid. Please enter number in range of 1950 to 2100\n";
                        }
                        int month_int = Integer.parseInt(eventMonth.getText().toString());
                        if (month_int < 1 || month_int >12){
                            err_msg = err_msg + "Month is not valid. Please enter number in range of 1 to 12\n";
                        }
                        int date_int = Integer.parseInt(eventDate.getText().toString());
                        if (date_int < 1 || date_int >31){
                            err_msg = err_msg + "Date is not valid. Please enter number in range of 1 to 31\n";
                        }
                        int time_int = Integer.parseInt(eventTime.getText().toString());
                        if (time_int < 0 || time_int >2359){
                            err_msg = err_msg + "Time is not valid. Please enter number in range of 0000 to 2359\n";
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
                int year_int = Integer.parseInt(eventYear.getText().toString());
                if (year_int < 1950 || year_int >2100){
                    err_msg = err_msg + "Year is not valid. Please enter number in range of 1950 to 2100\n";
                }
                int month_int = Integer.parseInt(eventMonth.getText().toString());
                if (month_int < 1 || month_int >12){
                    err_msg = err_msg + "Month is not valid. Please enter number in range of 1 to 12\n";
                }
                int date_int = Integer.parseInt(eventDate.getText().toString());
                if (date_int < 1 || date_int >31){
                    err_msg = err_msg + "Date is not valid. Please enter number in range of 1 to 31\n";
                }
                int time_int = Integer.parseInt(eventTime.getText().toString());
                if (time_int < 0 || time_int >2359){
                    err_msg = err_msg + "Time is not valid. Please enter number in range of 0000 to 2359\n";
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
                Events events;
                String text = "";
                eventCellTV = popupView.findViewById(R.id.eventListTV);
                for (int i = 0; i < arrayList.size(); i +=1){
                    events = arrayList.get(i);
                    if ((cal.get(Calendar.MONTH) + 1) == Integer.parseInt(events.getMonth())){
                        text = text + "\nEvent Name: " + events.getEvent() + "\nEvent Location: " + events.getLocation()
                                + "\nEvent Time: " + events.getTime() + "\nEvent Date: " + events.getDate() + " " + events.getMonth()
                                + " " + events.getYear() + "\n\n";
                    }
                }
                eventCellTV.setText(text);
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

//    private DatePickerDialog.OnDateSetListener myDateListener = new
//            DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker arg0,
//                                      int arg1, int arg2, int arg3) {
//                    // TODO Auto-generated method stub
//                    // arg1 = year
//                    // arg2 = month
//                    // arg3 = day
//                    showDate(arg1, arg2+1, arg3);
//                }
//            };
//
//    private void showDate(int year, int month, int day) {
//        eventCellTV.setText(new StringBuilder().append(day).append("/")
//                .append(month).append("/").append(year));
//    }


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
