package com.example.fitrition.boundary;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitrition.control.SearchManager;
import com.example.fitrition.entities.Eatery;

public class ExplorerPage extends AppCompatActivity {

    public void updateDisplay(String filter){
        //Change the Text on the text view to Map/list
        //update the display to reflect the list/map
    }

    public void applyFilter(){
        //Pop up bar should show with option to apply filter
        //filter consist of the extracted filter
        String filter = null;
        updateDisplay(filter);
    }

    public void displayHelp(){
        //Display the help FAQ
    }
    /*
    public void searchForLocation(){
        EditText e = (EditText) findViewById(R.id.text);
        String searchString = e.getText().toString();

        //Call the search manager to search retrieve the eatery
        SearchManager s = SearchManager.getInstance();
        Eatery eatery = s.retrieveEateryFromDB(searchString);

        updateDisplay(eatery.getName());

    }

     */
    public void toProfilePage(){
        //Redirect users to profile page when button is pressed
    }

    public void exploreFacility(){
        //When the facility/eatery is pressed it should pop up
        //to allow the user to rate the place / view more details
    }
}

