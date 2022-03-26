package com.example.fitrition.boundary;

import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitrition.control.AchievementManager;
import com.example.fitrition.control.SearchManager;
import com.example.fitrition.entities.Friend;
import com.example.fitrition.entities.IndividualUser;
import com.google.android.material.textfield.TextInputEditText;


public class ProfilePage extends AppCompatActivity {
    IndividualUser user = null;

    public ProfilePage(){
        SearchManager search = SearchManager.getInstance();
        //Search for the individual user and load his profile from the DB
        IndividualUser user = search.retrieveIndividualUser("User");
        this.user = user;
        displayInfo();
    }

    private void displayInfo(){
        //set all the fields in the profile page

        //TextView nameField = (TextView) findViewById(R.id.text2);
        //nameField.setText(user.getFirstName());

    }

    private void setDescription(){
        //text2 is not the actual object id. It just to show the flow
        //EditText input = (EditText) findViewById(R.id.text2);
        //String newDescription = input.getText().toString();
        //user.setDescription(newDescription);
    }

    public void dateOfBirth(){

    }

    public void setHeight(){

    }

    public void setWeight(){

    }
    public void setDietaryPreference(){

    }

    public void setUserPhoto(){

    }

    public void setName(){

    }

    public void setResidentialAddress(){

    }

    public void setEmailAddress(){

    }

    public void setGoals(){

    }

    /*
    public void addFriend(){
        SearchManager s = SearchManager.getInstance();

        //Retrieve userID from Text Field
        EditText input = (EditText) findViewById(R.id.text2);
        String friendUser = input.getText().toString();

        //Retrieve friend
        Friend friend = s.retrieveFriendByUsername(friendUser);

        //store the friend

    }
     */





}
