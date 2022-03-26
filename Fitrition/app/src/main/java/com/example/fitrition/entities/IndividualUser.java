package com.example.fitrition.entities;

import java.util.ArrayList;

public class IndividualUser{
    private String userName;
    private ArrayList<Achievement> acheivementList;
    private ArrayList<String> friendList;
    private ArrayList<Status> socialStatus;
	private String userID;
	private String description;
	private String firstName;
	private String lastName;
	private String password;
	private String DOB;
	private boolean isVerified;
	private String email;
	private String address;
	private String height;
	private String weight;
	private String gender;
	private String diet; //change to ennumeration

	public IndividualUser(String userName, ArrayList<Achievement> acheivementList, ArrayList<String> friendList, ArrayList<Status> socialStatus, String userID, String description, String firstName, String lastName, String password, String DOB, boolean isVerified, String email, String address, String height, String weight, String gender, String diet) {
		this.userName = userName;
		this.acheivementList = acheivementList;
		this.friendList = friendList;
		this.socialStatus = socialStatus;
		this.userID = userID;
		this.description = description;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.DOB = DOB;
		this.isVerified = isVerified;
		this.email = email;
		this.address = address;
		this.height = height;
		this.weight = weight;
		this.gender = gender;
		this.diet = diet;
	}

	public IndividualUser(String userName, String firstName, String lastName, String eMail, String password, String DOB, String height, String weight, String description,String gender,String diet){
		this.userName=userName;
		this.firstName=firstName;
		this.lastName=lastName;
		this.email=eMail;
		this.password=password;
		this.DOB=DOB;
		this.height=height;
		this.weight=weight;
		this.description=description;
		this.userName = userName;
		this.acheivementList = new ArrayList<Achievement>();
		this.friendList = new ArrayList<String>();
		this.socialStatus = new ArrayList<Status>();
		this.isVerified = false;
		this.gender=gender;
		this.diet=diet;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ArrayList<Achievement> getAcheivementList() {
		return acheivementList;
	}

	public void setAcheivementList(ArrayList<Achievement> acheivementList) {
		this.acheivementList = acheivementList;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String DOB) {
		this.DOB = DOB;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean verified) {
		isVerified = verified;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}
}
