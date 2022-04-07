package com.example.fitrition.boundary;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.fitrition.R;
import com.example.fitrition.utils.CalendarCustomView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TrackerFragment extends Fragment {

    LinearLayout layoutCalender;
    View custom_view;
    View viewCopy;
    Date date;
    int n = 0;
    int colorId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

                Toast.makeText(view.getContext(), "Date: " + dateOnly, Toast.LENGTH_LONG).show();
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
                SimpleDateFormat dateFormatDay= new SimpleDateFormat("dd ");
                String forDay = dateFormatDay.format(date);
                SimpleDateFormat dateFormatMonth= new SimpleDateFormat("MM");
                String forMonth = dateFormatMonth.format(date);
                SimpleDateFormat dateFormatYear= new SimpleDateFormat("yyyy");
                String forYear = dateFormatYear.format(date);
                eventDate.setText(forDay);
                eventMonth.setText(forMonth);
                eventYear.setText(forYear);

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });

                colorId = ((ColorDrawable) view.getBackground()).getColor();
                view.setBackgroundColor(Color.parseColor("#326e62"));
                viewCopy = view;
                n = 1;
            }
        });

    }
}