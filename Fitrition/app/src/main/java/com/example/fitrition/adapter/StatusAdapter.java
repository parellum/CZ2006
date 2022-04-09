package com.example.fitrition.adapter;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitrition.FriendActivity;
import com.example.fitrition.R;
import com.example.fitrition.control.FriendManager;
import com.example.fitrition.entities.Friend;
import com.example.fitrition.entities.Status;

import java.util.ArrayList;
import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    ArrayList<Status> statusList;
    ArrayList<String> friendIDList;
    ArrayList<Friend> friendList;
    Context context;
    FriendManager friendManager;

    public StatusAdapter(ArrayList<Status>statusList,ArrayList<String> friendIDList,ArrayList<Friend> friendList)
    {
        this.statusList = statusList;
        this.friendIDList=friendIDList;
        this.friendList=friendList;
    }

    public void setData(ArrayList<Status>statusList,ArrayList<String> friendIDList,ArrayList<Friend> friendList){
        this.statusList = statusList;
        this.friendIDList=friendIDList;
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
        holder.timeStatus.setText(status.getTime());
        int friendpos = friendIDList.indexOf(status.getUserID());
        Friend friend = friendList.get(friendpos);
        Glide.with(holder.friendImage.getContext())
                .load(friend.getImageUrl())
                .into(holder.friendImage);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendManager=FriendManager.getInstance();
                friendManager.setFriend(friend);
                Log.d(TAG, "onClick: hello");
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
        TextView textStatus,timeStatus;
        CardView cv;
        ImageView friendImage;

        public ViewHolder(View itemView)
        {
            super(itemView);
            //imgTvShow = (ImageView)itemView.findViewById(R.id.imgTvshow);
            textStatus = (TextView)itemView.findViewById(R.id.status_text);
            cv = (CardView)itemView.findViewById(R.id.cv);
            friendImage=itemView.findViewById(R.id.status_image);
            timeStatus=itemView.findViewById(R.id.status_time);
        }

    }

}
