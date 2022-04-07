package com.example.fitrition;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitrition.adapter.StatusAdapter;
import com.example.fitrition.adapter.StatusFocusAdapter;
import com.example.fitrition.control.FriendManager;
import com.example.fitrition.databinding.ActivityMainBinding;
import com.example.fitrition.entities.Friend;
import com.example.fitrition.utils.SpacingItemDecoration;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class FriendActivity extends AppCompatActivity {
// do not need this activity anymore
    private ActivityMainBinding binding;
    private Toolbar fToolbar;
    private ImageView backIcon;

    private TextView userName,age,gender,description;
    private ImageView friendImage;

    private FriendManager friendManager;

    RecyclerView recyclerView;
    StatusFocusAdapter statusFocusAdapter;

//    private EditText mSearchField;
//    private ImageButton mSearchBtn;
//
//    private RecyclerView mResultList;

//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mSearchField = (EditText) findViewById(R.id.search_field);
//        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);
//        mResultList = (RecyclerView) findViewById(R.id.result_list);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_status);

        friendManager=FriendManager.getInstance();

        Friend friendSubject = friendManager.getFriend();

        backIcon = findViewById(R.id.status_back_icon);

        userName = findViewById(R.id.friend_status_userName);
        age = findViewById(R.id.friend_status_age);
        gender = findViewById(R.id.friend_status_gender);
        description = findViewById(R.id.friend_status_description);

        friendImage = findViewById(R.id.friend_status_image);

        userName.setText(friendSubject.getUserName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDate date = LocalDate.parse(friendSubject.getDob(), formatter);
        Period intervalPeriod = Period.between(date,LocalDate.now());
        int ageInYears=intervalPeriod.getYears();

        age.setText(Integer.toString(ageInYears));
        gender.setText(friendSubject.getGender());
        description.setText(friendSubject.getDescription());

        statusFocusAdapter = new StatusFocusAdapter(friendSubject.getSocialStatus());

        recyclerView = (RecyclerView)findViewById(R.id.friend_social_feed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SpacingItemDecoration itemDecoration = new SpacingItemDecoration(10);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(statusFocusAdapter);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fToolbar = findViewById(R.id.friend_toolbar);
        setSupportActionBar(fToolbar);

    }


}