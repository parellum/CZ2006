package com.example.fitrition.boundary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fitrition.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
//
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.LoginRegister);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.LoginRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

        }
    }
}