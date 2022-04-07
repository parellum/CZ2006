package com.example.fitrition.entities;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FitnessCentreJSON {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("latitude")
    @Expose
    public String latitude;
    @SerializedName("longitude")
    @Expose
    public String longitude;


    //Added

    @SerializedName("Url")
    @Expose
    public String url;

    @SerializedName("Address")
    @Expose
    public String Address;

    @SerializedName("OpeningTime")
    @Expose
    public int OpeningTime;

    @SerializedName("ClosingTime")
    @Expose
    public int ClosingTime;

    @SerializedName("Description")
    @Expose
    public String Description;

    @SerializedName("Rating")
    @Expose
    public double Rating;

    //End
}

