package com.example.fitrition.boundary;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fitrition.R;
import com.example.fitrition.control.ProfileManager;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class ProfileFragment extends Fragment {
    Button button1;
    Button button2;
    Button changePassword;
    Button changeDescription;
    TextView name,age,email,description,gender,statusCount,friendCount;
    EditText password;
    ProfileManager profileManager;
    ImageView profilePic;

    int update=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileManager=ProfileManager.getInstance();

        button1 = view.findViewById(R.id.status_count_btn);
        button2 = view.findViewById(R.id.friend_list_btn);

        changeDescription = view.findViewById(R.id.profile_description_change);
        changePassword = view.findViewById(R.id.profile_password_change);

        name = (TextView) view.findViewById(R.id.profile_user_name);
        age = (TextView) view.findViewById(R.id.profile_user_age);
        email = (TextView) view.findViewById(R.id.profile_user_email);
        description = (TextView) view.findViewById(R.id.profile_user_description);
        gender = (TextView) view.findViewById(R.id.profile_user_gender);
        statusCount = (TextView) view.findViewById(R.id.profile_status_count);
        friendCount = (TextView) view.findViewById(R.id.profile_friend_count);

        password = (EditText) view.findViewById(R.id.profile_user_password);

        profilePic = (ImageView) view.findViewById(R.id.profile_user_image);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment passwordChangeFragment = new PasswordChangeFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_container, passwordChangeFragment).commit();
            }
        });

        changeDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment descriptionChangeFragment = new DescriptionChangeFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_container, descriptionChangeFragment).commit();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment statusHistory = new StatusHistoryFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_container, statusHistory).commit();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),FriendListActivity.class));
            }
        });

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name.setText(profileManager.getUser().getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDate date = LocalDate.parse(profileManager.getUser().getDob(), formatter);
        Period intervalPeriod = Period.between(date,LocalDate.now());
        int ageInYears=intervalPeriod.getYears();

        age.setText(Integer.toString(ageInYears));
        email.setText(profileManager.getUser().getEmail());
        description.setText(profileManager.getUser().getDescription());
        gender.setText(profileManager.getUser().getGender());

        friendCount.setText(Integer.toString(profileManager.getUser().getFriendList().size()));
        statusCount.setText(Integer.toString(profileManager.getUser().getSocialStatus().size()));

        password.setText(profileManager.getUser().getPassword());
        Log.d(TAG, "onViewCreated: "+profileManager.getUser().getImageUrl());

        Glide.with(view.getContext())
                .load(profileManager.getUser().getImageUrl())
                .into(profilePic);
    }
}