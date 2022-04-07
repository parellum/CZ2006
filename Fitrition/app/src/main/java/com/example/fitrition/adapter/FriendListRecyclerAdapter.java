package com.example.fitrition.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitrition.R;
import com.example.fitrition.entities.Friend;

import java.util.List;

public class FriendListRecyclerAdapter extends RecyclerView.Adapter<FriendListRecyclerAdapter.ViewHolder> {
    private List<Friend> userList;

    public FriendListRecyclerAdapter(List<Friend>userList){
        this.userList=userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friendlist_item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resource=userList.get(position).getFriend_image();
        String name=userList.get(position).getName();
        String description=userList.get(position).getDescription();
        String divider=userList.get(position).getFriendlist_divider();

        holder.setData(resource,name, description, divider);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView friendImage;
        private TextView friendName;
        private TextView friendDescrp;
        private TextView friendDiv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            friendImage=itemView.findViewById(R.id.friend_image);
            friendName=itemView.findViewById(R.id.name_textview);
            friendDescrp=itemView.findViewById(R.id.description_textview);
            friendDiv=itemView.findViewById(R.id.friendlist_divider);
        }
        public void setData(int resource, String name, String description, String divider){
            friendImage.setImageResource(resource);
            friendName.setText(name);
            friendDescrp.setText(description);
        }
    }
}
