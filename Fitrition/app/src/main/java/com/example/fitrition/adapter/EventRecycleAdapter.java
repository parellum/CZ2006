package com.example.fitrition.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitrition.FriendActivity;
import com.example.fitrition.R;
import com.example.fitrition.control.CalendarManager;
import com.example.fitrition.control.FriendManager;
import com.example.fitrition.control.SocialManager;
import com.example.fitrition.entities.Events;
import com.example.fitrition.entities.Friend;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;

public class EventRecycleAdapter extends RecyclerView.Adapter<EventRecycleAdapter.ViewHolder> {
    private List<Events> eventsList;
    private CalendarManager calendarManager;
    private SocialManager socialManager;

    public EventRecycleAdapter(List<Events>eventsList){
        this.eventsList=eventsList;
    }
    public void setData(List<Events> eventsList){ this.eventsList=eventsList;}

    @NonNull
    @Override
    public EventRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventlist_item_design,parent,false);
        return new EventRecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventRecycleAdapter.ViewHolder holder, int position) {

        Events event = eventsList.get(position);
        int eventIndex=eventsList.indexOf(event);

        if (eventIndex==0){
            holder.eventCardHead.setVisibility(View.VISIBLE);
            holder.eventHead.setText(monthValuetoMonth(event.getMonth())+" "+event.getYear());
        }else if (!eventsList.get(eventIndex-1).getMonth().equals(event.getMonth()) | !eventsList.get(eventIndex-1).getYear().equals(event.getYear())){
            holder.eventCardHead.setVisibility(View.VISIBLE);
            holder.eventHead.setText(monthValuetoMonth(event.getMonth())+" "+event.getYear());
        } else{
            holder.eventCardHead.setVisibility(View.GONE);
        }

        holder.setData(event.getEvent(),event.getLocation(),event.getDate()+"/"+event.getMonth()+"/"+event.getYear()+" "+event.getTime(), event.getExercise());

        if (event.getDone()==true){
            holder.eventCheck.setChecked(true);
        } else{
            holder.eventCheck.setChecked(false);
        }

        holder.eventCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true & event.getPosted()==false){
                    socialManager=SocialManager.getInstance();
                    socialManager.createStatusFromEvent(event);
                }
                event.setDone(b);
                event.setPosted(true);
                FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("events").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(event.getId()).setValue(event);

            }
        });

        holder.eventDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("events").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(event.getId()).removeValue();
                eventsList.remove(event);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    private String monthValuetoMonth(String s){
        switch(s) {
            case "01":
                return "Jan";
            case "02":
                return "Feb";
            case "03":
                return "Mar";
            case "04":
                return "Apr";
            case "05":
                return "May";
            case "06":
                return "Jun";
            case "07":
                return "Jul";
            case "08":
                return "Aug";
            case "09":
                return "Sep";
            case "10":
                return "Oct";
            case "11":
                return "Nov";
            case "12":
                return "Dec";
        }
        return "Error";
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        protected ImageView eventDine;
        protected ImageView eventGym;
        private TextView eventName;
        private TextView eventTime;
        private TextView eventLocation;
        private CardView eventCard;
        private CheckBox eventCheck;
        private TextView eventDelete;

        private CardView eventCardHead;
        private TextView eventHead;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventDine=itemView.findViewById(R.id.event_dine);
            eventGym=itemView.findViewById(R.id.event_gym);
            eventDine.setVisibility(View.GONE);
            eventGym.setVisibility(View.GONE);
            eventCard=itemView.findViewById(R.id.event_list_cv);
            eventName=itemView.findViewById(R.id.event_activity);
            eventTime=itemView.findViewById(R.id.event_time);
            eventLocation=itemView.findViewById(R.id.event_location);
            eventCheck=itemView.findViewById(R.id.event_check);
            eventCardHead=itemView.findViewById(R.id.event_list_date_div_cv);
            eventHead=itemView.findViewById(R.id.event_list_date_div);
            eventDelete=itemView.findViewById(R.id.event_delete);
        }
        public void setData( String name,String location, String time, Boolean isExercise){
            eventName.setText(name);
            eventTime.setText(time);
            eventLocation.setText(location);
            if(isExercise==true)
                eventGym.setVisibility(View.VISIBLE);
            else
                eventDine.setVisibility(View.VISIBLE);
        }
    }
}
