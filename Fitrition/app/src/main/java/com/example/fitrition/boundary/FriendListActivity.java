package com.example.fitrition.boundary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.fitrition.R;
import com.example.fitrition.adapter.FriendListRecyclerAdapter;
import com.example.fitrition.control.FriendManager;
import com.example.fitrition.entities.Friend;

import java.util.ArrayList;
import java.util.List;

public class FriendListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<Friend> userList;
    FriendListRecyclerAdapter friend_list_adapter;
    ImageView back_button;
    ImageView searchfriend_button;
    FriendManager friendManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        friendManager=FriendManager.getInstance();

        initData();
        initRecylerView();
        back_button=findViewById(R.id.friendlist_back_icon);
        searchfriend_button = findViewById(R.id.search_friend_icon);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        searchfriend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FriendListActivity.this, FriendAddNewActivity.class));
            }
        });
    }



    private void initData() {
        userList = new ArrayList<>();
        userList = friendManager.getFriendList();
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

    @Override
    protected void onResume() {
        super.onResume();
        userList = friendManager.getFriendList();
        friend_list_adapter.setData(userList);
        friend_list_adapter.notifyDataSetChanged();
    }
}