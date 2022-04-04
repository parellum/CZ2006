package com.example.fitrition;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fitrition.boundary.LoginActivity;
import com.example.fitrition.boundary.TrackerFragment;
import com.example.fitrition.boundary.ExploreFragment;
import com.example.fitrition.boundary.ProfileFragment;
import com.example.fitrition.boundary.SocialFragment;
import com.example.fitrition.databinding.ActivityMainBinding;
import com.example.fitrition.utils.HelpActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,new ExploreFragment()).commit();
        BottomNavigationView bottomNav = findViewById(R.id.main_bot_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //trying out without toolbar
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);


//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("  Fitrition");
//        actionBar.setIcon(R.mipmap.fitrition_logo);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);




// this is used when we want to click a BUTTON and it goes to the other activity
//        btnActivityMain = (Button)findViewById(R.id.help);
//        btnActivityMain.setOnClickListener((v)->{
//            startActivity(new Intent(MainActivity.this, HelpActivity.class));
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.help:
                startActivity(new Intent(this,LoginActivity.class));
//                Toast.makeText(this, "Help selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.log_out:
                Toast.makeText(this, "Log Out selected", Toast.LENGTH_SHORT).show();
                return true;
        }
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment=null;

            switch (item.getItemId()){
                case R.id.nav_explore:
                    selectedFragment=new ExploreFragment();
                    break;
                case R.id.nav_calendar:
                    selectedFragment=new TrackerFragment();
                    break;
                case R.id.nav_social:
                    selectedFragment=new SocialFragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment=new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,selectedFragment).commit();

            return true;
        }
    };

}