package com.example.fitrition.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitrition.boundary.FriendActivity;
import com.example.fitrition.R;
import com.example.fitrition.control.FriendManager;
import com.example.fitrition.entities.Friend;

import java.util.List;

public class FriendListRecyclerAdapter extends RecyclerView.Adapter<FriendListRecyclerAdapter.ViewHolder> {
    private List<Friend> userList;
    private FriendManager friendManager;
    public FriendListRecyclerAdapter(List<Friend>userList){
        this.userList=userList;
    }

    public void setData(List<Friend> userList){ this.userList=userList;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friendlist_item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        int resource=userList.get(position).getImageUrl();
        String name=userList.get(position).getName();
        String description=userList.get(position).getDescription();
//        String divider=userList.get(position).getFriendlist_divider();

        Friend targetFriend = userList.get(position);

        if (userList.get(position).getImageUrl()!=null) {
            Glide.with(holder.friendImage.getContext())
                    .load(userList.get(position).getImageUrl())
                    .into(holder.friendImage);
        }
        holder.setData(name, description);


        holder.friendCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendManager = FriendManager.getInstance();
                friendManager.setFriend(targetFriend);
                view.getContext().startActivity(new Intent(view.getContext(), FriendActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
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

        private ImageView friendImage;
        private TextView friendName;
        private TextView friendDescrp;
        private TextView friendDiv;
        private CardView friendCv;

        CardView cvHead;
        TextView textHead;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            friendImage=itemView.findViewById(R.id.friend_image);
            friendCv=itemView.findViewById(R.id.friend_list_cv);
            friendName=itemView.findViewById(R.id.name_textview);
            friendDescrp=itemView.findViewById(R.id.description_textview);
//            friendDiv=itemView.findViewById(R.id.friendlist_divider);

        }
        public void setData( String name, String description){
            friendName.setText(name);
            friendDescrp.setText(description);
        }
    }
}
