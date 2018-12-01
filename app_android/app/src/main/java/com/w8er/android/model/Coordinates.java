package com.w8er.android.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class Coordinates implements Parcelable {

    private double lat;
    private double lng;

    public Coordinates(){}

    public Coordinates(Location location){
        this.lat = location.getLatitude();
        this.lng = location.getLongitude();
    }



    protected Coordinates(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }
}
