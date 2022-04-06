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

}

