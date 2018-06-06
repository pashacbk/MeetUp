
package com.MeetUp.meetup.data.db.room.entities;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class KeyPhoto {

    @ColumnInfo(name = "photo_id")
    private int id;
    @SerializedName("highres_link")
    private String highresLink;
    @SerializedName("photo_link")
    private String photoLink;
    @SerializedName("thumb_link")
    private String thumbLink;
    @SerializedName("type")
    private String type;
    @SerializedName("base_url")
    private String baseUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHighresLink() {
        return highresLink;
    }

    public void setHighresLink(String highresLink) {
        this.highresLink = highresLink;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public String getThumbLink() {
        return thumbLink;
    }

    public void setThumbLink(String thumbLink) {
        this.thumbLink = thumbLink;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}
