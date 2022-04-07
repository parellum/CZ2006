package com.example.fitrition.adapter;

import android.content.Context;
import android.media.metrics.Event;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fitrition.R;
import com.example.fitrition.entities.Events;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends ArrayAdapter<Events>
{
    Context context;
    ArrayList<Events> arrayList;
    public EventAdapter(@NonNull Context context, List<Events> events)
    {
        super(context, 0, events);
        this.context = context;
        this.arrayList = arrayList;
    }


    public int getItemCount() {
        return arrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Events event = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_event_list, parent, false);

        TextView eventCellTV = convertView.findViewById(R.id.eventListTV);

//        String eventTitle = event.getEvent() +" "+ CalendarUtils.formattedTime(event.getTime());
        Events events = arrayList.get(position);
        eventCellTV.setText(arrayList.toString());
        return convertView;
    }
}
