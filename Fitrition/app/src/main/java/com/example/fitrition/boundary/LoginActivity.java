package com.example.fitrition.boundary;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fitrition.LoadinDialogBar;
import com.example.fitrition.MainActivity;
import com.example.fitrition.R;
import com.example.fitrition.control.CalendarManager;
import com.example.fitrition.control.FacilityManager;
import com.example.fitrition.control.ProfileManager;
import com.example.fitrition.utils.HelpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
//
    private TextView register,resetPassword;
    private EditText editTextEmail, editTextPassword;
    private Button login;
    private ProfileManager profileManager;
    private FacilityManager facilityManager;
    private CalendarManager calendarManager;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.LoginRegister);
        register.setOnClickListener(this);
        resetPassword = (TextView) findViewById(R.id.LoginForgot);
        resetPassword.setOnClickListener(this);

        login=(Button) findViewById(R.id.LoginBtn);
        login.setOnClickListener(this);
        editTextEmail=(EditText) findViewById(R.id.LoginEmail);
        editTextPassword=(EditText) findViewById(R.id.LoginPassword);
        progressBar=(ProgressBar) findViewById(R.id.RegisterPB);

        mAuth=FirebaseAuth.getInstance();
        profileManager = ProfileManager.getInstance();
        facilityManager = FacilityManager.getInstance();
        calendarManager = CalendarManager.getInstance();

        facilityManager.loadFacilities();
        facilityManager.loadFood();





    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.LoginRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.LoginBtn:

                userLogin();
                break;
            case R.id.LoginForgot:

                startActivity(new Intent(this,ResetActivity.class));
                break;
        }
    }

    private void userLogin(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

//        if (!email.isEmpty()){
//            startActivity(new Intent(LoginActivity.this,MainActivity.class));
//            return;
//        }

        if (email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length()<6){
            editTextPassword.setError("Min password length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    profileManager.loadUser(mAuth.getCurrentUser().getUid(), LoginActivity.this);
                    calendarManager.loadEvents();
                    Log.d(TAG, "onComplete: "+profileManager.getUser().getImageUrl());
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
                else{
                    Toast.makeText(LoginActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}