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
