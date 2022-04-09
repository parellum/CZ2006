package com.example.fitrition.boundary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitrition.R;
import com.example.fitrition.control.ProfileManager;
import com.example.fitrition.entities.IndividualUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PasswordChangeFragment extends Fragment {

    View view;
    EditText pwOld,pwNew,pwNewRepeat;
    Button pwSave;
    Button pwBack;

    FirebaseAuth mAuth;

    DatabaseReference mDataRef;

    ProfileManager profileManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_password_change, container, false);

        pwOld=view.findViewById(R.id.pw_old);
        pwNew=view.findViewById(R.id.pw_new);
        pwNewRepeat=view.findViewById(R.id.pw_new_repeat);

        pwSave=view.findViewById(R.id.pw_save);
        pwBack=view.findViewById(R.id.pw_back);

        profileManager=ProfileManager.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pwSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IndividualUser subject=profileManager.getUser();
                if (!pwOld.getText().toString().trim().equals(subject.getPassword())){
                    pwOld.setError("Password does not match with current password");
                    pwOld.requestFocus();
                    return;
                }
                if (pwNew.getText().toString().trim().length()<6){
                    pwNew.setError("Please provide valid entry");
                    pwNewRepeat.setError("New password and password confirmation do not match");
                    pwNew.requestFocus();
                    return;
                }

                if (!pwNew.getText().toString().trim().equals(pwNewRepeat.getText().toString().trim())){
                    pwNew.setError("New password and password confirmation do not match");
                    pwNewRepeat.setError("New password and password confirmation do not match");
                    pwNew.requestFocus();
                    return;
                }

                mAuth=FirebaseAuth.getInstance();

                mAuth.getCurrentUser().updatePassword(pwNew.getText().toString().trim());

                FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users").child(mAuth.getCurrentUser().getUid()).child("password").setValue(pwNew.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(view.getContext(), "Password changed", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(view.getContext(), "Server error. Password not changed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        pwBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ProfileFragment()).commit();
            }
        });



    }
}