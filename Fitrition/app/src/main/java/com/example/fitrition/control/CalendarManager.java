package com.example.fitrition.control;

import androidx.annotation.NonNull;

import com.example.fitrition.entities.Events;
import com.example.fitrition.entities.FitnessCentreJSON;
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
    private StorageReference mStorageReference;

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

    public void loadEvents(){
        mDatabaseReference= FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    Events eventChild=ds.getValue(Events.class);
                    eventsList.add(eventChild);
                }
                mDatabaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
