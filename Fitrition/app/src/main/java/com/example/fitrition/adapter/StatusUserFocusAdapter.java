package com.example.fitrition.adapter;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitrition.R;
import com.example.fitrition.control.IndividualUserManager;
import com.example.fitrition.entities.Status;

import java.util.ArrayList;

public class StatusUserFocusAdapter extends RecyclerView.Adapter<StatusUserFocusAdapter.ViewHolder> {
    Context context;
    ArrayList<Status> statusList;
    private IndividualUserManager individualUserManager;

    public StatusUserFocusAdapter(ArrayList<Status> statusList)
    {
        this.statusList = statusList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateView: yo");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        individualUserManager = IndividualUserManager.getInstance();
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Status status = statusList.get(position);
        holder.textStatus.setText(status.getDescription());
        holder.textTime.setText(status.getDay()+"/"+status.getMonth()+"/"+status.getYear()+" "+status.getTime());
        if (individualUserManager.getUser().getImageUrl()!=null) {
            Glide.with(holder.statusImage.getContext())
                    .load(individualUserManager.getUser().getImageUrl())
                    .into(holder.statusImage);
        }

        if (position==0){
            holder.cvHead.setVisibility(View.VISIBLE);
            holder.textHead.setText(monthValuetoMonth(status.getMonth())+" "+status.getYear());
        }else if (!statusList.get(position-1).getMonth().equals(status.getMonth()) | !statusList.get(position-1).getYear().equals(status.getYear())){
            holder.cvHead.setVisibility(View.VISIBLE);
            holder.textHead.setText(monthValuetoMonth(status.getMonth())+" "+status.getYear());
        }else{
            holder.cvHead.setVisibility(View.GONE);
        }


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

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //ImageView imgTvShow;
        TextView textStatus,textTime;
        ImageView statusImage;
        CardView cv;

        CardView cvHead;
        TextView textHead;




        public ViewHolder(View itemView)
        {
            super(itemView);
            //imgTvShow = (ImageView)itemView.findViewById(R.id.imgTvshow);
            textStatus = (TextView)itemView.findViewById(R.id.status_text);
            textTime = (TextView) itemView.findViewById(R.id.status_time);
            statusImage = (ImageView) itemView.findViewById(R.id.status_image);
            cv = (CardView)itemView.findViewById(R.id.cv);

            cvHead=itemView.findViewById(R.id.list_date_div_cv);
            textHead=itemView.findViewById(R.id.list_date_div);
        }

    }
}
