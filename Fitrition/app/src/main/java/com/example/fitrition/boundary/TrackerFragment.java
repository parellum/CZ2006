package com.example.fitrition.boundary;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fitrition.R;
import com.example.fitrition.adapter.EventRecycleAdapter;
import com.example.fitrition.adapter.FriendListRecyclerAdapter;
import com.example.fitrition.control.CalendarManager;
import com.example.fitrition.control.FriendManager;
import com.example.fitrition.entities.Events;
import com.example.fitrition.entities.Friend;
import com.example.fitrition.utils.CalendarCustomView;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TrackerFragment extends Fragment {

    LinearLayout layoutCalender;
    View custom_view;
    View viewCopy;
    Date date;
    int n = 0;
    int colorId;
    TextView currentDay;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<Events> eventsList;
    EventRecycleAdapter tracker_day_recycle_manager;
    CalendarManager calendarManager;

    Calendar cal;

    Button addEventButton;

    int hour, minute;
    LocalTime time;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);

        setInitializations(view);
        setCalenderView(view);

        addEventButton=view.findViewById(R.id.buttonAddEvent);
        currentDay = view.findViewById(R.id.display_current_day);
        recyclerView = view.findViewById(R.id.tracker_day_recycle);
        eventsList=new ArrayList<Events>();
        calendarManager= CalendarManager.getInstance();


        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Singapore"));
        cal=Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"));

        SimpleDateFormat dateFormat= new SimpleDateFormat("dd MMMM yyyy");
        String dateOnly = dateFormat.format(cal.getTime());
        currentDay.setText(dateOnly);

        initRecylerView(view,dateOnly);

        Button allEvent = view.findViewById(R.id.buttonAllEvent);
        allEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,new EventsHistoryFragment()).commit();
            }
        });

        return view;
    }

    private void initRecylerView(View view,String dateStr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);

        LocalDate date = LocalDate.parse(dateStr, formatter);


        ArrayList<Events> eventsList = new ArrayList<Events>();
        ArrayList<Integer> intList=new ArrayList<Integer>();

        for (Events subject:calendarManager.getEventsList()){
            if(date.getMonthValue()==Integer.parseInt(subject.getMonth()) & date.getDayOfMonth()==Integer.parseInt(subject.getDate()) & date.getYear()==Integer.parseInt(subject.getYear())){
                eventsList.add(subject);
                intList.add(Integer.parseInt(subject.getTime()));
            }
        }
        Collections.sort(intList);
        ArrayList<Events> sortedArray = new ArrayList<Events>();

        for (Integer subject:intList){
            for (Events sub:eventsList){
                if(subject==Integer.parseInt(sub.getTime())){
                    sortedArray.add(sub);
                    eventsList.remove(sub);
                    break;
                }
            }
        }

        recyclerView=view.findViewById(R.id.tracker_day_recycle);
        layoutManager=new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        tracker_day_recycle_manager=new EventRecycleAdapter(sortedArray);
        recyclerView.setAdapter(tracker_day_recycle_manager);
    }

    private void updateRecycler(View view,String dateStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);

        LocalDate date = LocalDate.parse(dateStr, formatter);

        ArrayList<Events> eventsList = new ArrayList<Events>();
        ArrayList<Integer> intList=new ArrayList<Integer>();

        for (Events subject:calendarManager.getEventsList()){
            if(date.getMonthValue()==Integer.parseInt(subject.getMonth()) & date.getDayOfMonth()==Integer.parseInt(subject.getDate()) & date.getYear()==Integer.parseInt(subject.getYear())){
                eventsList.add(subject);
                intList.add(Integer.parseInt(subject.getTime()));
            }
        }
        Collections.sort(intList);
        ArrayList<Events> sortedArray = new ArrayList<Events>();

        for (Integer subject:intList){
            for (Events sub:eventsList){
                if(subject==Integer.parseInt(sub.getTime())){
                    sortedArray.add(sub);
                    eventsList.remove(sub);
                    break;
                }
            }
        }

        tracker_day_recycle_manager.setData(sortedArray);
        tracker_day_recycle_manager.notifyDataSetChanged();
    }

    private void setInitializations(View view) {
        custom_view = (View) view.findViewById(R.id.custom_view);
        layoutCalender = (LinearLayout) view.findViewById(R.id.layoutCalender);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
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


                TextView eventName = (TextView) popupView.findViewById(R.id.eventname);
                TextView eventLocation = (TextView) popupView.findViewById(R.id.eventlocation);
                Button eventTime = (Button) popupView.findViewById(R.id.timeButton);
                EditText eventDate = (EditText) popupView.findViewById(R.id.eventdatebox);
                EditText eventMonth = (EditText) popupView.findViewById(R.id.eventmonthbox);
                EditText eventYear = (EditText) popupView.findViewById(R.id.eventyearbox);
                Button saveEventButton = (Button) popupView.findViewById(R.id.buttonSaveEvent);

                RadioButton eventDine = popupView.findViewById(R.id.add_event_radio_dine);

                TimeZone.setDefault(TimeZone.getTimeZone("Asia/Singapore"));
                cal=Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"));

                SimpleDateFormat dateFormat= new SimpleDateFormat("dd MMMM yyyy");
                String dateOnly = dateFormat.format(cal.getTime());

                String calDay=Integer.toString(cal.get(Calendar.DATE));
                String calMonth=Integer.toString(cal.get(Calendar.MONTH)+1);

                if (calDay.length()==1){
                    calDay="0"+calDay;
                }
                if (calMonth.length()==1){
                    calMonth="0"+calMonth;
                }

                eventDate.setText(calDay);
                eventMonth.setText(calMonth);
                eventYear.setText(Integer.toString(cal.get(Calendar.YEAR)));

                eventTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
                        {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                            {
                                hour = selectedHour;
                                minute = selectedMinute;
                                time = LocalTime.of(hour, minute);
                                Button eventTime = (Button) view.findViewById(R.id.timeButton);
                                eventTime.setText(time.toString());
                            }
                        };

                        int style = AlertDialog.THEME_HOLO_LIGHT;

                        TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), style, onTimeSetListener, hour, minute, true);

                        timePickerDialog.setTitle("Select Time");
                        timePickerDialog.show();
                    }
                });


                saveEventButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View popupView) {
                        String err_msg = "";

                        String newName = eventName.getText().toString().trim();
                        String newLocation = eventLocation.getText().toString().trim();
                        String newTime = eventTime.getText().toString().replaceAll(":","").trim();
                        String newDay=eventDate.getText().toString().trim();
                        String newMonth = eventMonth.getText().toString().trim();
                        String newYear = eventYear.getText().toString().trim();
                        Boolean isExercise;

                        if (eventDine.isChecked()){
                            isExercise=false;
                        }else{
                            isExercise=true;
                        }


                        if (eventName.getText().toString().trim().isEmpty()) {
                            err_msg = err_msg + "Name is not valid. Please enter the event name.\n";
                            eventName.setError(err_msg);
                            eventName.requestFocus();
                            return;
                        }
                        if (eventLocation.getText().toString().trim().isEmpty()) {
                            err_msg = err_msg + "Location is not valid. Please enter a valid location.\n";
                            eventLocation.setError(err_msg);
                            eventLocation.requestFocus();
                            return;
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
                            calendarManager.saveAnEvent(new Events(newName,newLocation,newTime,newDay,newMonth,newYear,isExercise));
                            updateRecycler(view,dateOnly);
                            popupWindow.dismiss();
                        }
                        else {
                            Toast.makeText(view.getContext(), err_msg, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
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

                addEventButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
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
                        Button eventTime = (Button) popupView.findViewById(R.id.timeButton);

                        eventTime.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
                                {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                                    {
                                        hour = selectedHour;
                                        minute = selectedMinute;
                                        time = LocalTime.of(hour, minute);
                                        Button eventTime = (Button) view.findViewById(R.id.timeButton);
                                        eventTime.setText(time.toString());
                                    }
                                };

                                int style = AlertDialog.THEME_HOLO_LIGHT;

                                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), style, onTimeSetListener, hour, minute, true);

                                timePickerDialog.setTitle("Select Time");
                                timePickerDialog.show();
                            }
                        });

                        RadioButton eventDine = popupView.findViewById(R.id.add_event_radio_dine);

                        saveEventButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View popupView) {
                                String newName = eventName.getText().toString().trim();
                                String newLocation = eventLocation.getText().toString().trim();
                                String newTime = eventTime.getText().toString().replaceAll(":","").trim();
                                String newDay=eventDate.getText().toString().trim();
                                String newMonth = eventMonth.getText().toString().trim();
                                String newYear = eventYear.getText().toString().trim();
                                Boolean isExercise;

                                if (eventDine.isChecked()){
                                    isExercise=false;
                                }else{
                                    isExercise=true;
                                }

                                String err_msg = "";
                                if (eventName.getText().toString().trim().isEmpty()) {
                                    err_msg = err_msg + "Name is not valid. Please enter the event name.\n";
                                    eventName.setError(err_msg);
                                    eventName.requestFocus();
                                    return;
                                }
                                if (eventLocation.getText().toString().trim().isEmpty()) {
                                    err_msg = err_msg + "Location is not valid. Please enter a valid location.\n";
                                    eventLocation.setError(err_msg);
                                    eventLocation.requestFocus();
                                    return;
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
                                    calendarManager.saveAnEvent(new Events(newName,newLocation,newTime,newDay,newMonth,newYear,isExercise));
                                    updateRecycler(view,dateOnly);
                                    popupWindow.dismiss();
                                }
                                else {
                                    Toast.makeText(view.getContext(), err_msg, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });



                currentDay.setText(dateOnly);

                updateRecycler(view,dateOnly);

                colorId = ((ColorDrawable) view.getBackground()).getColor();
                view.setBackgroundColor(Color.parseColor("#326e62"));
                viewCopy = view;
                n = 1;
            }
        });

    }
}