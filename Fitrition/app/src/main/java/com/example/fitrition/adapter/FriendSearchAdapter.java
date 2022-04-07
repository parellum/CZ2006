package com.example.fitrition.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitrition.FriendActivity;
import com.example.fitrition.R;
import com.example.fitrition.control.FriendManager;
import com.example.fitrition.entities.Friend;
import com.example.fitrition.entities.Status;

import java.util.ArrayList;

public class FriendSearchAdapter extends RecyclerView.Adapter<FriendSearchAdapter.ViewHolder> {
    ArrayList<Status> statusList;
    ArrayList<String> friendIDList;
    ArrayList<Friend> friendList;
    Context context;
    FriendManager friendManager;

    public FriendSearchAdapter(ArrayList<Friend> friendList)
    {
        this.friendList=friendList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Status status = statusList.get(position);
        holder.textStatus.setText(status.getDescription());

        int friendpos = friendIDList.indexOf(status.getUserID());
        Friend friend = friendList.get(friendpos);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendManager.setFriend(friend);
                view.getContext().startActivity(new Intent(view.getContext(), FriendActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //ImageView imgTvShow;
        TextView textStatus;
        CardView cv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            //imgTvShow = (ImageView)itemView.findViewById(R.id.imgTvshow);
            textStatus = (TextView)itemView.findViewById(R.id.status_text);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }

    }

}
