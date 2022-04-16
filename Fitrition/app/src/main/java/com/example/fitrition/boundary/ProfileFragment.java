package com.example.fitrition.boundary;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fitrition.R;
import com.example.fitrition.control.IndividualUserManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

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
    IndividualUserManager individualUserManager;
    ImageView profilePic;
    private Uri mImageUri;

    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;

    int update=0;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        individualUserManager = IndividualUserManager.getInstance();

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

        mStorageRef = FirebaseStorage.getInstance("gs://fitrition-3a967.appspot.com/").getReference("imageupload");
        mDatabaseRef = FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
                }
        });

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

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK
                && data!=null && data.getData()!=null){
            mImageUri=data.getData();
            Picasso.get().load(mImageUri).into(profilePic);

            Dialog dialog = new Dialog(getContext());
            dialog.setTitle("Change Profile Picture");
            dialog.setContentView(R.layout.fragment_invitation);
            dialog.show();

            Button btnAccept = dialog.findViewById(R.id.acceptBtn);
            Button btnDecline = dialog.findViewById(R.id.declineBtn);
            TextView popquizz = dialog.findViewById(R.id.popUpTitle);

            popquizz.setText("Do you want to keep your change?");

            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadFile(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    dialog.dismiss();
                }
            });

            btnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Glide.with(view.getContext())
                            .load(individualUserManager.getUser().getImageUrl())
                            .into(profilePic);
                    dialog.dismiss();
                }
            });

        }
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name.setText(individualUserManager.getUser().getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDate date = LocalDate.parse(individualUserManager.getUser().getDob(), formatter);
        Period intervalPeriod = Period.between(date,LocalDate.now());
        int ageInYears=intervalPeriod.getYears();

        age.setText(Integer.toString(ageInYears));
        email.setText(individualUserManager.getUser().getEmail());
        description.setText(individualUserManager.getUser().getDescription());
        gender.setText(individualUserManager.getUser().getGender());

        friendCount.setText(Integer.toString(individualUserManager.getUser().getFriendList().size()));
        statusCount.setText(Integer.toString(individualUserManager.getUser().getSocialStatus().size()));

        password.setText(individualUserManager.getUser().getPassword());
        Log.d(TAG, "onViewCreated: "+ individualUserManager.getUser().getImageUrl());

        Glide.with(view.getContext())
                .load(individualUserManager.getUser().getImageUrl())
                .into(profilePic);
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getView().getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(String uid){
        if (mImageUri!=null){
            StorageReference fileReference = mStorageRef.child(uid
                    +"."+getFileExtension(mImageUri));

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    mDatabaseRef.child(uid).child("imageUrl").setValue(url);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getView().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            Toast.makeText(getView().getContext(), "Uploading...", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(getView().getContext(),"No file selected!",Toast.LENGTH_SHORT);
        }
    }
}