package com.example.fitrition.adapter;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitrition.R;
import com.example.fitrition.control.CalendarManager;
import com.example.fitrition.entities.Events;
import com.example.fitrition.entities.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class ViewingOfHealthyFoodAdapter extends RecyclerView.Adapter<ViewingOfHealthyFoodAdapter.ViewHolder>{

    private List<Food> foodList;
    private DatabaseReference mDataRef;
    int hour, minute;
    LocalTime time;
    Calendar cal;

    public ViewingOfHealthyFoodAdapter(List<Food> foodList){
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ViewingOfHealthyFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewing_of_healthy_food_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewingOfHealthyFoodAdapter.ViewHolder holder, int position) {
        String URL = foodList.get(position).getFoodImageUrl();
        String name = foodList.get(position).getNameOfFood();
        String description=foodList.get(position).getDescription();
        Double calories =foodList.get(position).getCalories();
        String nameOfStall = foodList.get(position).getNameOfStall();

        String location = foodList.get(position).getNameOfHawker();

        Glide.with(holder.foodAdapterImageOfDish.getContext())
                .load(foodList.get(position).getFoodImageUrl())
                .into(holder.foodAdapterImageOfDish);

        holder.setData(URL, name, description, calories);

        holder.foodAdapterAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
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

                eventName.setText(name);
                eventLocation.setText(location);

                saveEventButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View popupView) {
                        String err_msg = "";
                        String newTime = eventTime.getText().toString().replaceAll(":","").trim();

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

                        if (newTime.equals("Select Time")){
                            err_msg = err_msg + "Please select a time.\n";
                            eventTime.setError(err_msg);
                            eventTime.requestFocus();
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
                            SaveEvent(eventName.getText().toString(), eventLocation.getText().toString()
                                    , eventTime.getText().toString().replaceAll(":","")
                                    , eventDate.getText().toString(), eventMonth.getText().toString()
                                    , eventYear.getText().toString());
                            popupWindow.dismiss();
                        }
                        else {
                            Toast.makeText(view.getContext(), err_msg, Toast.LENGTH_LONG).show();
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

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        protected ImageView foodAdapterImageOfDish;
        private TextView foodAdapterTextNameOfDish;
        private TextView foodAdapterTextDescription;
        private TextView foodAdapterTextCalories;
        Button foodAdapterAddEvent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodAdapterImageOfDish=itemView.findViewById(R.id.foodAdapterImageDish);
            foodAdapterTextNameOfDish=itemView.findViewById(R.id.foodAdapterTextNameOfDish);
            foodAdapterTextDescription=itemView.findViewById(R.id.foodAdapterTextDescription);
            foodAdapterTextCalories=itemView.findViewById(R.id.foodAdapterTextCalories);

            //DIsgusting BUtton
            foodAdapterAddEvent = (Button) itemView.findViewById(R.id.foodAdapterAddEvent);

        }

        public void setData(String URL, String name, String description, double calories){
            //foodAdapterImageOfDish.setImageResource();
            foodAdapterTextNameOfDish.setText(name);
            foodAdapterTextDescription.setText(description);
            foodAdapterTextCalories.setText(Double.toString(calories));
        }

    }

    private void SaveEvent(String event, String location,String time,String date, String month, String year){
        //Events events = new Events(event,location,time,date,month,year,false);
        CalendarManager.getInstance().saveAnEvent(new Events(event,location,time,date,month,year,false));


       /* mDataRef= FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("events").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mDataRef.child(Long.toString(Calendar.getInstance().getTimeInMillis())).setValue(events).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //Toast.makeText(context, "Successfully Saved", Toast.LENGTH_SHORT).show();
            }
        });*/

    }

}
