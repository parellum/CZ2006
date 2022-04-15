package com.example.fitrition.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.fitrition.R;

public class HelpActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        backIcon = findViewById(R.id.help_back_icon);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                startActivity(new Intent(HelpActivity.this, MainActivity.class));
//                NavUtils.navigateUpFromSameTask(HelpActivity.this);
            }
        });



        mToolbar = findViewById(R.id.help_toolbar);
        setSupportActionBar(mToolbar);


//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Help Page");
//        //actionBar.setIcon(R.mipmap.fitrition_logo);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
    }

}