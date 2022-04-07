package com.example.fitrition.entities;

import java.util.ArrayList;

public class IndividualUser{
    private String userName;
    private ArrayList<String> friendList;
    private ArrayList<Status> socialStatus;
	private String userID;
	private String description;
	private String name;
	private String password;
	private String dob;
	private boolean verified;
	private String email;
	private String gender;
	private String imageUrl;

	public IndividualUser(){
		//Needed for firebase
	}

	public IndividualUser(String userName, String name, String eMail, String password, String dob, String description, String gender, String imageUrl, ArrayList<String> friendList, ArrayList<Status> socialStatus){
		this.userName=userName;
		this.name = name;
		this.email=eMail;
		this.password=password;
		this.dob = dob;
		this.description=description;
		this.userName = userName;
		this.friendList = friendList;
		this.socialStatus = socialStatus;
		this.verified = false;
		this.gender=gender;
		this.imageUrl=imageUrl;
	}

	public IndividualUser(String userName, String name, String eMail, String password, String dob, String description, String gender){
		this.userName=userName;
		this.name = name;
		this.email=eMail;
		this.password=password;
		this.dob = dob;
		this.description=description;
		this.userName = userName;
		this.friendList = new ArrayList<String>();
		this.socialStatus = new ArrayList<Status>();
		this.verified = false;
		this.gender=gender;
	}


	public ArrayList<String> getFriendList() {
		return friendList;
	}

	public void setFriendList(ArrayList<String> friendList) {
		this.friendList = friendList;
	}

	public ArrayList<Status> getSocialStatus() {
		return socialStatus;
	}

	public void setSocialStatus(ArrayList<Status> socialStatus) {
		this.socialStatus = socialStatus;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
