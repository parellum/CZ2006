package com.example.fitrition.utils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fitrition.MainActivity;
import com.example.fitrition.R;

public class FriendFragment extends AppCompatActivity {

    private Toolbar fToolbar;
    private ImageView backIcon;

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


        backIcon = findViewById(R.id.status_back_icon);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendFragment.this, MainActivity.class));
            }
        });

        fToolbar = findViewById(R.id.friend_toolbar);
        setSupportActionBar(fToolbar);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Help Page");
//        //actionBar.setIcon(R.mipmap.fitrition_logo);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
    }

}
