package com.example.fitrition.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitrition.R;
import com.example.fitrition.control.FriendManager;
import com.example.fitrition.entities.Status;

import java.util.ArrayList;

public class StatusFocusAdapter extends RecyclerView.Adapter<StatusFocusAdapter.ViewHolder> {
    Context context;
    ArrayList<Status> statusList;
    private FriendManager friendManager;

    public StatusFocusAdapter(ArrayList<Status> statusList)
    {
        this.statusList = statusList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        friendManager = FriendManager.getInstance();
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Status status = statusList.get(position);
        holder.textStatus.setText(status.getDescription());
        holder.textTime.setText(status.getTime());
        Glide.with(holder.statusImage.getContext())
                .load(friendManager.getFriend().getImageUrl())
                .into(holder.statusImage);

        //holder.imgTvShow.setImageResource(tvShow.getImgTvshow());
//        holder.cv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // use friendmanager conditional
////                Toast.makeText(context,"The position is:"+position,Toast.LENGTH_SHORT).show();
//                view.getContext().startActivity(new Intent(view.getContext(), FriendActivity.class));
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //ImageView imgTvShow;
        TextView textStatus,textTime;
        ImageView statusImage;
        CardView cv;

        public ViewHolder(View itemView)
        {
            super(itemView);
            //imgTvShow = (ImageView)itemView.findViewById(R.id.imgTvshow);
            textStatus = (TextView)itemView.findViewById(R.id.status_text);
            textTime = (TextView) itemView.findViewById(R.id.status_time);
            statusImage = (ImageView) itemView.findViewById(R.id.status_image);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }

    }
}
