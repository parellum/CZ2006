package com.example.fitrition.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class IndividualUser{
    private String userName;
    private ArrayList<String> friendList;
    private ArrayList<Status> socialStatus;
    private ArrayList<String> friendRequest;
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
		friendList=new ArrayList<String>();
		socialStatus=new ArrayList<Status>();
		friendRequest = new ArrayList<String>();
	}

	public IndividualUser(String userID,String userName, String name, String eMail, String password, String dob, String description, String gender){
		this.userID=userID;
		this.userName=userName;
		this.name = name;
		this.email=eMail;
		this.password=password;
		this.dob = dob;
		this.description=description;
		this.userName = userName;
		this.friendList = new ArrayList<String>();
		this.socialStatus = new ArrayList<Status>();
		String minute = Integer.toString(LocalDateTime.now().getMinute());
		if (minute.length()==1){
			minute="0"+minute;
		}
		String hour =Integer.toString(LocalDateTime.now().plusHours(8).getHour());
		if (hour.length()==1){
			hour="0"+hour;
		}
		String day=Integer.toString(LocalDateTime.now().plusHours(8).getDayOfMonth());
		if (day.length()==1){
			day="0"+day;
		}
		String month = Integer.toString(LocalDateTime.now().plusHours(8).getMonthValue());
		if (month.length()==1){
			month="0"+month;
		}
		socialStatus.add(new Status(userName+" just joined Fitrition!",Integer.toString(LocalDateTime.now().plusHours(8).getYear()),month,day,hour+minute));
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

	public ArrayList<String> getFriendRequest() {
		return friendRequest;
	}

	public void setFriendRequest(ArrayList<String> friendRequest) {
		this.friendRequest = friendRequest;
	}
}
