package com.example.fitrition.control;
import com.example.fitrition.entities.Eatery;
import com.example.fitrition.entities.Friend;
import com.example.fitrition.entities.IndividualUser;
import com.example.fitrition.entities.Plan;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SearchManager {
    private static SearchManager instance = null;
    private Connection con = null;


    public static SearchManager getInstance() {
        if (instance == null) {
            instance = new SearchManager();
        }
        return instance;
    }

    //Establish connection with the database
    public void establishConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/database_name","root","root");
            //here sonoo is database name, root is username and password
            this.con = con;

            //con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    public Eatery retrieveEateryFromDB(String eateryName){
        return null;
    }

    public Plan retrievePlanOnADate(LocalDate dateOfInterest){
        return null;
    }

    public Friend retrieveFriendByUsername(String userName){

        return null;
    }

    public IndividualUser retrieveIndividualUser(String userID){
        String sqlStatement = "select * FROM user WHERE name =" + userID ;
        IndividualUser user = null;

        try{
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(sqlStatement);
            while(rs.next()){
                //search for user
                //user = new IndividualUser();
                user = null;
            }



        }catch(Exception e){ System.out.println(e);}

        return user;
    }

    //Business user search is removed
}
