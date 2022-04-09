package com.example.fitrition.boundary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitrition.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText resetEmail;
    Button resetBack,resetCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        resetEmail = findViewById(R.id.ResetEmail);
        resetBack = findViewById(R.id.ResetBack);
        resetCall = findViewById(R.id.ResetCall);

        resetBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        resetCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(resetEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Reset E-mail sent. Check your inbox.", Toast.LENGTH_LONG).show();
                                }else if (task.isCanceled()){
                                    Toast.makeText(ForgotPasswordActivity.this, "Invalid E-mail address", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(ForgotPasswordActivity.this, "Invalid E-mail address", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}