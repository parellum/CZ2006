package com.example.fitrition.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitrition.FriendActivity;
import com.example.fitrition.R;
import com.example.fitrition.entities.Status;
import com.example.fitrition.utils.HelpActivity;

import java.text.BreakIterator;
import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    List<Status> statusList;
    Context context;

    public StatusAdapter(List<Status>statusList)
    {
        this.statusList = statusList;
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
        //holder.imgTvShow.setImageResource(tvShow.getImgTvshow());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // use friendmanager conditional
//                Toast.makeText(context,"The position is:"+position,Toast.LENGTH_SHORT).show();
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
            textStatus = (TextView)itemView.findViewById(R.id.textStatus);
            cv = (CardView)itemView.findViewById(R.id.cv);
        }

    }

}
