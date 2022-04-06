package com.example.fitrition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ViewFacilities extends AppCompatActivity {
    private TextView viewFacilitiesName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_facilities);

        viewFacilitiesName = (TextView) findViewById(R.id.viewFacilitiesName);

        Bundle b = getIntent().getExtras();
        String facilitiesName = null;
        if(b != null){
            facilitiesName = b.getString("facilitiesName");
            viewFacilitiesName.setText(facilitiesName);
        }

    }
}