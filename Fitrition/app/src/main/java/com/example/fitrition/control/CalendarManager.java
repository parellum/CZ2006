package com.example.fitrition.control;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fitrition.entities.Events;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CalendarManager {
    private static CalendarManager instance=null;
    private ArrayList<Events> eventsList;

    private DatabaseReference mDatabaseReference;

    public  CalendarManager(){
        eventsList = new ArrayList<Events>();
    }

    public static CalendarManager getInstance() {
        if (instance == null) {
            instance = new CalendarManager();
        }
        return instance;
    }

    public ArrayList<Events> getEventsList() {
        return eventsList;
    }

    public void setEventsList(ArrayList<Events> eventsList) {
        this.eventsList = eventsList;
    }

    public void saveAnEvent(Events event){
        eventsList.add(event);
        mDatabaseReference= FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("events").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mDatabaseReference.child(event.getId()).setValue(event);
    }

    public void loadEvents(){
        mDatabaseReference= FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("events").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventsList=new ArrayList<Events>();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Events eventChild=ds.getValue(Events.class);
                    if (!eventsList.contains(eventChild)){
                        eventsList.add(eventChild);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
