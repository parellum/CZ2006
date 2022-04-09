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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class DescriptionChangeFragment extends Fragment {
    View view;

    EditText descriptionChange;
    Button descriptionSave,descriptionBack;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_description_change, container, false);

        descriptionChange=view.findViewById(R.id.description_change);
        descriptionSave=view.findViewById(R.id.description_save);
        descriptionBack=view.findViewById(R.id.description_back);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        descriptionSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = descriptionChange.getText().toString().trim();

                if (description.isEmpty()){
                    descriptionChange.setError("Please provide text.");
                    descriptionChange.requestFocus();
                    return;
                }

                FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("description").setValue(description).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(view.getContext(), "Description updated", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(view.getContext(), "Failed to update description", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        descriptionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ProfileFragment()).commit();
            }
        });

    }
}