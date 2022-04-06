package com.example.fitrition.boundary;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitrition.R;
import com.example.fitrition.control.ProfileManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private ImageView mImageView;
    private Uri mImageUri;


    private FirebaseAuth mAuth;
    private DatabaseReference userNameReference;
    private TextView registerTitle,registerFinish;
    private EditText editUserName,editFirstName, editLastName, editEMail, editPassword, editConfirmpassword, editDOB, editHeight, editWeight, editDescription;
    private Spinner editGender, editDiet;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mButtonChooseImage = (Button) findViewById(R.id.RegisterImageChoose);
        mImageView = (ImageView) findViewById(R.id.RegisterImage);

        mAuth = FirebaseAuth.getInstance();

        registerTitle = (TextView) findViewById(R.id.RegisterTitle);
        registerTitle.setOnClickListener(this);

        registerFinish = (Button) findViewById(R.id.RegisterFinish);
        registerFinish.setOnClickListener(this);

        editUserName = (EditText) findViewById(R.id.RegisterUserName);
        editFirstName = (EditText) findViewById(R.id.RegisterFirstName);
        editLastName = (EditText) findViewById(R.id.RegisterLastName);
        editEMail = (EditText) findViewById(R.id.RegisterEmail);
        editPassword = (EditText) findViewById(R.id.RegisterPassword);
        editConfirmpassword = (EditText) findViewById(R.id.RegisterConfirmPassword);
        editDOB = (EditText) findViewById(R.id.RegisterDOB);
        editHeight = (EditText) findViewById(R.id.RegisterHeight);
        editWeight = (EditText) findViewById(R.id.RegisterWeight);
        editDescription = (EditText) findViewById(R.id.RegisterDescription);

        editGender = (Spinner) findViewById(R.id.RegisterGender);
        ArrayAdapter<CharSequence> adapterGender=ArrayAdapter.createFromResource(this,R.array.genders,android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_item);
        editGender.setAdapter(adapterGender);

        editDiet = (Spinner) findViewById(R.id.RegisterDiet);
        ArrayAdapter<CharSequence> adapterDiet=ArrayAdapter.createFromResource(this,R.array.dietaryPreference,android.R.layout.simple_spinner_item);
        adapterDiet.setDropDownViewResource(android.R.layout.simple_spinner_item);
        editDiet.setAdapter(adapterDiet);

        progressBar = (ProgressBar) findViewById(R.id.RegisterPB);

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });


    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK
        && data!=null && data.getData()!=null){
            mImageUri=data.getData();
            Picasso.get().load(mImageUri).into(mImageView);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.RegisterTitle:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.RegisterFinish:
                registerUser();
                break;
        }
    }

    private void registerUser(){
        ProfileManager profileManager = ProfileManager.getInstance();

        String userName = editUserName.getText().toString().trim().toLowerCase();
        String firstName = editFirstName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();
        String eMail = editEMail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String confirmPassword = editConfirmpassword.getText().toString().trim();
        String DOB = editDOB.getText().toString().trim();
        String height = editHeight.getText().toString().trim();
        String weight = editWeight.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String gender = editGender.getSelectedItem().toString().trim();
        String diet = editDiet.getSelectedItem().toString().trim();



        //Do more error checking

        if (userName.isEmpty()){
            editUserName.setError("User Name is required!");
            editUserName.requestFocus();
            return;
        }

        if (userName.length()<6){
            editUserName.setError("Username needs to be at least 6 characters long!");
            editUserName.requestFocus();
            return;
        }

        //check unique username
        userNameReference=FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("usernames");
        userNameReference.orderByKey().equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    editUserName.setError("Username is already taken!");
                    editUserName.requestFocus();
                    return;
                }else{
                    if (firstName.isEmpty()){
                        editFirstName.setError("First Name is required!");
                        editFirstName.requestFocus();
                        return;
                    }

                    if (eMail.isEmpty()){
                        editEMail.setError("Email is required!");
                        editEMail.requestFocus();
                        return;
                    }

                    if (!Patterns.EMAIL_ADDRESS.matcher(eMail).matches()){
                        editEMail.setError("Please enter a valid email!");
                        editEMail.requestFocus();
                        return;
                    }

                    if (password.isEmpty()){
                        editPassword.setError("Password is required!");
                        editPassword.requestFocus();
                        return;
                    }

                    if (password.length()<6){
                        editPassword.setError("Password needs to be at least 6 characters long!");
                        editPassword.requestFocus();
                        return;
                    }

                    if (confirmPassword.compareTo(password)!=0){
                        editConfirmpassword.setError("Password and confirmation do not match!");
                        editConfirmpassword.requestFocus();
                        return;
                    }

                    if (DOB.isEmpty()){
                        editDOB.setError("Date of Birth is required!");
                        editDOB.requestFocus();
                        return;
                    }

                    try{
                        if (!LocalDate.of(Integer.parseInt(DOB.substring(4,8)),Integer.parseInt(DOB.substring(2,4)),Integer.parseInt(DOB.substring(0,2))).isBefore(LocalDate.now())){
                            editDOB.setError("Please Enter Valid Date in DDMMYYYY format");
                            editDOB.requestFocus();
                            return;
                        }
                    }catch (Exception e){
                        editDOB.setError("Please Enter Valid Date in DDMMYYYY format");
                        editDOB.requestFocus();
                        return;
                    }

                    if (height.isEmpty()){
                        editHeight.setError("Height is required!");
                        editHeight.requestFocus();
                        return;
                    }

                    if (!isNumeric(height)){
                        editHeight.setError("Digits only!");
                        editHeight.requestFocus();
                        return;
                    }

                    if (weight.isEmpty()){
                        editWeight.setError("Weight is required!");
                        editWeight.requestFocus();
                        return;
                    }
                    if (!isNumeric(weight)){
                        editWeight.setError("Digits only!");
                        editWeight.requestFocus();
                        return;
                    }



                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(eMail,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    int registerFollow = 0;
                                    if (task.isSuccessful()) {
                                        profileManager.addNewUser(userName, firstName, lastName, eMail, password, DOB, height, weight, description, gender, diet);

                                        FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(profileManager.getUser()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RegisterActivity.this, "User has been registered susccessfully!", Toast.LENGTH_LONG).show();
                                                    progressBar.setVisibility(View.GONE);
                                                } else {
                                                    Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                                    progressBar.setVisibility(View.GONE);
                                                }
                                            }
                                        });
                                        FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("usernames").child(userName)
                                                .setValue(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    progressBar.setVisibility(View.GONE);
                                                } else {
                                                    progressBar.setVisibility(View.GONE);
                                                }
                                            }
                                        });
                                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegisterActivity.this, "Connection time time out. Try again!", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}