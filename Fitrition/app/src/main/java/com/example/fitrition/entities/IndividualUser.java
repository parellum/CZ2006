package com.example.fitrition.entities;

import java.util.ArrayList;

public class IndividualUser {
    private String userID;
    private String userName;
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

    public void IndividualUser(String userID, String userName, String password, String confirmPassword, String email, String description, ArrayList<Achievement> acheivementList, ArrayList<Integer> friendList, Status[] socialStatus){
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.description = description;
    }

    public String userID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}

    public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

    public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
