package com.example.fitrition.entities;

import java.util.ArrayList;

public class IndividualUser extends User{
    private String userName;
    private ArrayList<Achievement> acheivementList;
    private ArrayList<Integer> friendList;
    private Status[] socialStatus=new Status[20];
    private String confirmPassword;

	public IndividualUser(String userID, String description, String firstName, String lastName, String password, boolean isVerified, String email, String address, String userName, ArrayList<Achievement> acheivementList, ArrayList<Integer> friendList, Status[] socialStatus, String confirmPassword) {
		super(userID, description, firstName, lastName, password, isVerified, email, address);
		this.userName = userName;
		this.acheivementList = acheivementList;
		this.friendList = friendList;
		this.socialStatus = socialStatus;
		this.confirmPassword = confirmPassword;
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

	public ArrayList<Integer> getFriendList() {
		return friendList;
	}

	public void setFriendList(ArrayList<Integer> friendList) {
		this.friendList = friendList;
	}

	public Status[] getSocialStatus() {
		return socialStatus;
	}

	public void setSocialStatus(Status[] socialStatus) {
		this.socialStatus = socialStatus;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
