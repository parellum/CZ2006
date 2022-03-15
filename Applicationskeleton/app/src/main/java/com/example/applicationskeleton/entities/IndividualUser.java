package com.example.applicationskeleton.entities;

import java.util.ArrayList;

public class IndividualUser {
    private int userID;
    private String description;
    private ArrayList<Achievement> acheivementList;
    private ArrayList<Integer> friendList;
    private Status[] socialStatus=new Status[20];
    private String firstName;
    private String lastName;
    private String password;
    private boolean isVerfied;
    private String email;
    private String address;
    private String confirmPassword;
}
