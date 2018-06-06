
package com.MeetUp.meetup.data.db.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "groups")
public class Group{

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "topic_id")
    private String topicId;
    @SerializedName("name")
    private String name;
    @SerializedName("status")
    private String status;
    @SerializedName("link")
    private String link;
    @SerializedName("urlname")
    private String urlname;
    @SerializedName("description")
    private String description;
    @SerializedName("created")
    private long created;
    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;
    @SerializedName("localized_country_name")
    private String localizedCountryName;
    @SerializedName("localized_location")
    private String localizedLocation;
    @SerializedName("state")
    private String state;
    @SerializedName("join_mode")
    private String joinMode;
    @SerializedName("visibility")
    private String visibility;
    @SerializedName("lat")
    private String lat;
    @SerializedName("lon")
    private String lon;
    @SerializedName("members")
    private int members;
    @SerializedName("who")
    private String who;
    @SerializedName("key_photo")
    @Embedded
    private KeyPhoto keyPhoto;

    @SerializedName("timezone")
    private String timezone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

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

    public String getLocalizedLocation() {
        return localizedLocation;
    }

    public void setLocalizedLocation(String localizedLocation) {
        this.localizedLocation = localizedLocation;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getJoinMode() {
        return joinMode;
    }

    public void setJoinMode(String joinMode) {
        this.joinMode = joinMode;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
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

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public KeyPhoto getKeyPhoto() {
        return keyPhoto;
    }

    public void setKeyPhoto(KeyPhoto keyPhoto) {
        this.keyPhoto = keyPhoto;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
