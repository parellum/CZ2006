package com.example.fitrition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.fitrition.databinding.ActivityMainBinding;
import com.example.fitrition.R;
import com.example.fitrition.MainActivity;
import com.example.fitrition.utils.FriendFragment;
import com.example.fitrition.utils.HelpActivity;

public class FriendActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Toolbar fToolbar;
    private ImageView backIcon;

    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private RecyclerView mResultList;

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
        setContentView(R.layout.fragment_list_layout);


        backIcon = findViewById(R.id.status_back_icon);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendActivity.this, MainActivity.class));
            }
        });

        fToolbar = findViewById(R.id.friend_toolbar);
        setSupportActionBar(fToolbar);

    }


}