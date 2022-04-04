package com.example.fitrition.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fitrition.MainActivity;
import com.example.fitrition.R;
import com.example.fitrition.boundary.ExploreFragment;
import com.example.fitrition.boundary.ProfileFragment;
import com.example.fitrition.boundary.SocialFragment;
import com.example.fitrition.boundary.TrackerFragment;
import com.example.fitrition.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HelpActivity extends AppCompatActivity {

//    private ActivityMainBinding binding;
//    private Toolbar mToolbar;
//    private ImageView backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

//
//        backIcon = findViewById(R.id.back_icon);
//
//        backIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HelpActivity.this, MainActivity.class));
//            }
//        });


//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Help Page");
//        //actionBar.setIcon(R.mipmap.fitrition_logo);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
    }

}