package com.w8er.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Coordinates implements Parcelable {

    private String lat;
    private String lng;

    public Coordinates(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Coordinates(Parcel in) {
        lat = in.readString();
        lng = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lat);
        dest.writeString(lng);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Coordinates> CREATOR = new Creator<Coordinates>() {
        @Override
        public Coordinates createFromParcel(Parcel in) {
            return new Coordinates(in);
        }

        @Override
        public Coordinates[] newArray(int size) {
            return new Coordinates[size];
        }
    };

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
