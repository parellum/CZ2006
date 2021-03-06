package com.example.fitrition.control;

import com.example.fitrition.entities.IndividualUser;

import java.util.ArrayList;

/**
 * ProfileManager is in charge of talking with UI to display the registration page, login page, and the forget password page
 * @author Teresa
 * @version 1.0
 * @since 24-03-2022
 */

public class ProfileManager {

    private static ProfileManager instance=null;
    ArrayList<IndividualUser> individualUserList=new ArrayList<IndividualUser>();

    public ProfileManager() {
        individualUserList=new ArrayList<IndividualUser>();
    }

    public static ProfileManager getInstance() {
        if (instance == null) {
            instance = new ProfileManager();
        }
        return instance;
    }

    public ArrayList<IndividualUser> getIndividualUserList() {
        return individualUserList;
    }

    public void setIndividualUserList(ArrayList<IndividualUser> individualUserList) {
        this.individualUserList = individualUserList;
    }

    public void addNewUser(String userName, String firstName, String lastName, String eMail, String password, String DOB, String height, String weight, String description,String gender,String diet){
        individualUserList.add(new IndividualUser(userName,firstName,lastName,eMail,password,DOB,height,weight,description,gender,diet));
    }

    public IndividualUser getUser(){
        return individualUserList.get(0);
    }

    public boolean validatePassword(String str){
        return str.matches("\\S");
    }

    public boolean validateUserName(String str){
        return str.matches("[a-zA-Z0-9]*");
    }

    public boolean validateEmail(String str){
        return str.matches("\"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$\"");
    }

    /*
    public boolean validateAccount(String email, String password){

    }

    public boolean validateEmailAvailability(String email){

    }

    public void updateVerificationCode(String email, String verificationCode){

    }

    public boolean validateVerificationCode(String email, String verificationCode){

    }

    public boolean validateConfirmPassword(String password, String confirmPassword){

    }

     */
    public void updatePassword(String email, String password){

    }
    /*
    public void createUserAccount(String email, String userName, String name, String address, String password, boolean isVerified, String verificationCode){
        String username = newUserNameEditText.getText().toString();
        String displayedName = firstNameEditText.getText().toString() + " " + lastNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String confirmPW = confirmPwEditText.getText().toString();

        if (firstNameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {

            displayToast("A username and password are required.");

        } else if (!password.matches(confirmPW)){

            displayToast("Password does not match with the confirmed password.");
    }

     */
    public void updateIsVerified(String email, boolean isVerified){

    }


}