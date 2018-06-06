package com.MeetUp.meetup.api.models;

import com.google.gson.annotations.SerializedName;

public class LocationModel {

    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;
    @SerializedName("localized_country_name")
    private String localizedCountryName;
    @SerializedName("name_string")
    private String nameString;
    @SerializedName("lat")
    private String lat;
    @SerializedName("lon")
    private String lon;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocalizedCountryName() {
        return localizedCountryName;
    }

    public void setLocalizedCountryName(String localizedCountryName) {
        this.localizedCountryName = localizedCountryName;
    }

    public String getNameString() {
        return nameString;
    }

    public void setNameString(String nameString) {
        this.nameString = nameString;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

}
