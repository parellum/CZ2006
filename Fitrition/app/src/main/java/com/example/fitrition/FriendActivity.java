package com.example.fitrition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.fitrition.databinding.ActivityMainBinding;
import com.example.fitrition.R;
import com.example.fitrition.MainActivity;
import com.example.fitrition.utils.HelpActivity;

public class FriendActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Toolbar mToolbar;
    private ImageView backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);


        backIcon = findViewById(R.id.friend_back_icon);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendActivity.this, MainActivity.class));
            }
        });

        mToolbar = findViewById(R.id.friend_toolbar);
        setSupportActionBar(mToolbar);

    }

}