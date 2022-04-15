package com.example.fitrition.boundary;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitrition.R;
import com.example.fitrition.adapter.AllUserRecyclerAdapter;
import com.example.fitrition.control.ProfileManager;
import com.example.fitrition.entities.Friend;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendAddNewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<Friend> searchUserList;
    AllUserRecyclerAdapter all_user_list_adapter;
    ImageView back_button;
    ProfileManager profileManager;
    DatabaseReference mDatabaseReference;
    int allowUpdate=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_addnew);
        profileManager = ProfileManager.getInstance();

        initData();
        back_button=findViewById(R.id.all_user_back_icon);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void initData() {
        searchUserList = new ArrayList<>();
        mDatabaseReference= FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot targetChild:snapshot.getChildren()){
                    if (profileManager.getUser().getFriendList().contains(targetChild.getKey()) | profileManager.getUser().getUserID().equals(targetChild.getKey())){
                        continue;
                    }else{
                        searchUserList.add(targetChild.getValue(Friend.class));
                    }
                }
                initRecylerView();
                mDatabaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void initRecylerView() {
        recyclerView=findViewById(R.id.searchfriend_recyclerview);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        all_user_list_adapter=new AllUserRecyclerAdapter(searchUserList);
        recyclerView.setAdapter(all_user_list_adapter);
        all_user_list_adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        allowUpdate=1;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (allowUpdate == 1) {
            searchUserList = new ArrayList<>();
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot targetChild : snapshot.getChildren()) {
                        if (profileManager.getUser().getFriendList().contains(targetChild.getKey()) | profileManager.getUser().getUserID().equals(targetChild.getKey())) {
                            continue;
                        } else {
                            searchUserList.add(targetChild.getValue(Friend.class));
                        }
                    }
                    initRecylerView();
                    all_user_list_adapter.setData(searchUserList);
                    all_user_list_adapter.notifyDataSetChanged();
                    allowUpdate=0;
                    mDatabaseReference.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}