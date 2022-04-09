package com.example.fitrition.boundary;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitrition.R;
import com.example.fitrition.adapter.AllUserRecyclerAdapter;
import com.example.fitrition.adapter.FriendListRecyclerAdapter;
import com.example.fitrition.control.FriendManager;
import com.example.fitrition.control.ProfileManager;
import com.example.fitrition.entities.Achievement;
import com.example.fitrition.entities.Friend;
import com.example.fitrition.entities.Status;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.firestore.auth.User;

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

    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private RecyclerView mResultList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_addnew);
        profileManager = ProfileManager.getInstance();

        //added for search bar
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);
        mResultList = (RecyclerView) findViewById(R.id.searchfriend_recyclerview);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        initData();
        back_button=findViewById(R.id.all_user_back_icon);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = mSearchField.getText().toString();
                firebaseUserSearch(searchText);
            }
        });
    }

    // added
    private void firebaseUserSearch(String searchText) {

        Toast.makeText(FriendAddNewActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mDatabaseReference.orderByChild("username").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<Friend, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Friend, UsersViewHolder>(

                Friend.class,
                R.layout.friendlist_item_design,
                UsersViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Friend model, int position) {

                viewHolder.setDetails(getApplicationContext(), model.getUserName(), model.getDescription(), model.getImageUrl());

            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);

    }

    // View Holder Class
    public static class UsersViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setDetails(Context ctx, String userName, String userStatus, String userImage){
            TextView user_name = (TextView) mView.findViewById(R.id.name_textview);
            TextView user_status = (TextView) mView.findViewById(R.id.description_textview);
            ImageView user_image = (ImageView) mView.findViewById(R.id.friend_image);

            user_name.setText(userName);
            user_status.setText(userStatus);

            Glide.with(ctx).load(userImage).into(user_image);

        }
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
