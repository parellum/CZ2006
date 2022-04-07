package com.example.fitrition.boundary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.fitrition.R;
import com.example.fitrition.adapter.FriendListRecyclerAdapter;
import com.example.fitrition.entities.Achievement;
import com.example.fitrition.entities.Friend;
import com.example.fitrition.entities.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FriendListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<Friend> userList;
    FriendListRecyclerAdapter friend_list_adapter;
    ImageView back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        initData();
        initRecylerView();
        back_button=findViewById(R.id.friendlist_back_icon);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void initData() {
        userList = new ArrayList<>();
        userList.add(new Friend(R.drawable.ic_avocado_profile, "Jannie", "Hello!",new ArrayList<Status>(), new ArrayList<Achievement>(), "_____________________________________________"));
        userList.add(new Friend(R.drawable.ic_avocado_profile, "Tonny", "What's up!",new ArrayList<Status>(), new ArrayList<Achievement>(), "_____________________________________________"));
        userList.add(new Friend(R.drawable.ic_avocado_profile, "Jaden", "Looking for a run!",new ArrayList<Status>(), new ArrayList<Achievement>(), "_____________________________________________"));
        userList.add(new Friend(R.drawable.ic_avocado_profile, "Jia Xin", "Looking for a gym buddy!",new ArrayList<Status>(), new ArrayList<Achievement>(), "_____________________________________________"));
        userList.add(new Friend(R.drawable.ic_avocado_profile, "Darren", "Follow my instagram: darren_love_food",new ArrayList<Status>(), new ArrayList<Achievement>(), "_____________________________________________"));
        userList.add(new Friend(R.drawable.ic_avocado_profile, "Jannie", "Hello!",new ArrayList<Status>(), new ArrayList<Achievement>(), "_____________________________________________"));
        userList.add(new Friend(R.drawable.ic_avocado_profile, "Tonny", "What's up!",new ArrayList<Status>(), new ArrayList<Achievement>(), "_____________________________________________"));
        userList.add(new Friend(R.drawable.ic_avocado_profile, "Jaden", "Looking for a run!",new ArrayList<Status>(), new ArrayList<Achievement>(), "_____________________________________________"));
        userList.add(new Friend(R.drawable.ic_avocado_profile, "Jia Xin", "Looking for a gym buddy!",new ArrayList<Status>(), new ArrayList<Achievement>(), "_____________________________________________"));
        userList.add(new Friend(R.drawable.ic_avocado_profile, "Darren", "Follow my instagram: darren_love_food",new ArrayList<Status>(), new ArrayList<Achievement>(), "_____________________________________________"));
    }

    private void initRecylerView() {
        recyclerView=findViewById(R.id.friendlist_recyclerview);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        friend_list_adapter=new FriendListRecyclerAdapter(userList);
        recyclerView.setAdapter(friend_list_adapter);
        friend_list_adapter.notifyDataSetChanged();

    }
}