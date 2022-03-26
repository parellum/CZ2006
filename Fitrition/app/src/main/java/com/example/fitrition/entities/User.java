package com.example.fitrition.entities;
import java.util.ArrayList;

/**
 * Generalization of Individual and Business Users
 */
public abstract class User {
    private int userID;
    private String description;
    private String firstName;
    private String lastName;
    private String password;
    private boolean isVerified;
    private String email;
    private String address;

    /**
     * constructor for user
     * @param userID unique userID
     * @param description description set by user on account creation, mutable
     * @param firstName first name of registered user
     * @param lastName last name of registered user
     * @param password the password related to account, to be verified upon login
     * @param isVerified verification of authentic user with e-mail feedback
     * @param email the e-mail to be registered. Cannot be defaulted to empty
     * @param address the address of user that can be used to optimize suggestion. Can default to None
     */
    public User(int userID, String description, String firstName, String lastName, String password, boolean isVerified, String email, String address) {
        this.userID = userID;
        this.description = description;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.isVerified = isVerified;
        this.email = email;
        this.address = address;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
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

}
