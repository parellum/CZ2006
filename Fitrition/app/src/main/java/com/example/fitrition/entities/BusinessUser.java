package com.example.fitrition.entities;

import java.util.ArrayList;

/**
 * BusinessUser is specification for businesses which control facilities
 * @author Jacques
 * @version 1.0
 * @since 20-03-2022
 */
public class BusinessUser extends User{
    private ArrayList<Facility> facilityList;

    /**
     * Constructor for BusinessUser
     * @param userID
     * @param description
     * @param firstName
     * @param lastName
     * @param password
     * @param isVerified
     * @param email
     * @param address
     * @param facilityList The list of facilities owned by BusinessUser
     */
    public BusinessUser(String userID, String description, String firstName, String lastName, String password, boolean isVerified, String email, String address, ArrayList<Facility> facilityList) {
        super(userID, description, firstName, lastName, password, isVerified, email, address);
        this.facilityList = facilityList;
    }

    public ArrayList<Facility> getFacilityList() {
        return facilityList;
    }

    public void setFacilityList(ArrayList<Facility> facilityList) {
        this.facilityList = facilityList;
    }
}
