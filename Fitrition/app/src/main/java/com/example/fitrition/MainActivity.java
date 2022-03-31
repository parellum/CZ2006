package com.example.fitrition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fitrition.boundary.RegisterActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.fitrition.databinding.ActivityMainBinding;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    Button status, status1, status2, status3, status4, status5, status6,status7, status8, status9, status10;
    private ActivityMainBinding binding;
    private Button friendStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        friendStatus=(Button) findViewById(R.id.friendSatusBtn);
        friendStatus.setOnClickListener((View.OnClickListener) this);

        status1 = (Button)findViewById( R.id.btn1);
        status2 = (Button)findViewById( R.id.btn2 );
        status3 = (Button)findViewById( R.id.btn3 );
        status4 = (Button)findViewById( R.id.btn4 );
        status5 = (Button)findViewById( R.id.btn5 );
        status6 = (Button)findViewById( R.id.btn6 );
        status7 = (Button)findViewById( R.id.btn7 );
        status8 = (Button)findViewById( R.id.btn8 );
        status9 = (Button)findViewById( R.id.btn9 );
        status10 = (Button)findViewById( R.id.btn10 );

        status1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Activity.class);
                startActivity(i);
                finish();
            }
        });
        status2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Activity.class);
                startActivity(i);
                finish();
            }
        });
        status3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Activity.class);
                startActivity(i);
                finish();
            }
        });
        status4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Activity.class);
                startActivity(i);
                finish();
            }
        });
        status5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Activity.class);
                startActivity(i);
                finish();
            }
        });
        status6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Activity.class);
                startActivity(i);
                finish();
            }
        });
        status7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Activity.class);
                startActivity(i);
                finish();
            }
        });
        status8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Activity.class);
                startActivity(i);
                finish();
            }
        });
        status9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Activity.class);
                startActivity(i);
                finish();
            }
        });
        status10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Activity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.friendSatusBtn:

                break;
            case R.id.personal:
                break;
        }
    }

}