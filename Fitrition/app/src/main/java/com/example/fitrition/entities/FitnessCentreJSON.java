package com.example.fitrition.entities;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;




public class FitnessCentreJSON {

    @SerializedName("type")
    private Type type = null;

    public enum Type{
        @SerializedName("0")
        HAWKER,
        @SerializedName("1")
        FITNESS
    }

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("OpeningTime")
    @Expose
    private int OpeningTime;

    @SerializedName("ClosingTime")
    @Expose
    private int ClosingTime;

    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("Rating")
    @Expose
    private double Rating;

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    public FitnessCentreJSON(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getOpeningTime() {
        return OpeningTime;
    }

    public void setOpeningTime(int openingTime) {
        OpeningTime = openingTime;
    }

    public int getClosingTime() {
        return ClosingTime;
    }

    public void setClosingTime(int closingTime) {
        ClosingTime = closingTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public String getType() {
        return type.toString();
    }

    public void setType(String type) {
        if(type.equalsIgnoreCase("Hawker"))
            this.type = Type.HAWKER;
        else
            this.type = Type.FITNESS;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

