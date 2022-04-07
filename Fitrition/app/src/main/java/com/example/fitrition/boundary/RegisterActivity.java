package com.example.fitrition.boundary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
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
import com.example.fitrition.entities.IndividualUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private ImageView mImageView;
    private Uri mImageUri;

    private IndividualUser Iuser;

    private FirebaseAuth mAuth;
    private DatabaseReference userNameReference;
    private TextView registerTitle,registerFinish;
    private EditText editUserName, editName, editEMail, editPassword, editConfirmpassword, editDOB, editDescription;
    private Spinner editGender;
    private ProgressBar progressBar;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
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
        editName = (EditText) findViewById(R.id.RegisterName);
        editEMail = (EditText) findViewById(R.id.RegisterEmail);
        editPassword = (EditText) findViewById(R.id.RegisterPassword);
        editConfirmpassword = (EditText) findViewById(R.id.RegisterConfirmPassword);
        editDOB = (EditText) findViewById(R.id.RegisterDOB);
        editDescription = (EditText) findViewById(R.id.RegisterDescription);

        editGender = (Spinner) findViewById(R.id.RegisterGender);
        ArrayAdapter<CharSequence> adapterGender=ArrayAdapter.createFromResource(this,R.array.genders,android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_item);
        editGender.setAdapter(adapterGender);

        progressBar = (ProgressBar) findViewById(R.id.RegisterPB);

        mStorageRef = FirebaseStorage.getInstance("gs://fitrition-3a967.appspot.com/").getReference("imageupload");
        mDatabaseRef = FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
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

        String userName = editUserName.getText().toString().trim().toLowerCase();
        String name = editName.getText().toString().trim();
        String eMail = editEMail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String confirmPassword = editConfirmpassword.getText().toString().trim();
        String DOB = editDOB.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String gender = editGender.getSelectedItem().toString().trim();


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
                    if (name.isEmpty()){
                        editName.setError("First Name is required!");
                        editName.requestFocus();
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

                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(eMail,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    int registerFollow = 0;
                                    if (task.isSuccessful()) {
                                        Iuser=new IndividualUser(userName, name, eMail, password, DOB, description, gender);
                                        FirebaseDatabase.getInstance("https://fitrition-3a967-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(Iuser).addOnCompleteListener(new OnCompleteListener<Void>() {
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

                                        uploadFile(FirebaseAuth.getInstance().getCurrentUser().getUid());
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

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
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
                            String url = fileReference.getDownloadUrl().toString();
                            mDatabaseRef.child(uid).child("imageUrl").setValue(url);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            Toast.makeText(RegisterActivity.this, "Uploading...", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(this,"No file selected!",Toast.LENGTH_SHORT);
        }
    }
}