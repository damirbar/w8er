package com.w8er.android.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class LocationPoint implements Parcelable {
    private String[] coordinates;

    public LocationPoint() {
    }

    public LocationPoint(String lat, String lng) {
        coordinates = new String[]{lng,lat};
    }

    public LocationPoint(Location location) {

        if (location != null)
            coordinates = new String[]{String.valueOf(location.getLongitude()),String.valueOf(location.getLatitude())};
    }



    public void setLocationPoint(LatLng latLng) {
        if (latLng != null)
            coordinates = new String[]{String.valueOf(latLng.longitude),String.valueOf(latLng.latitude)};

    }


    public void setLocationPoint(String lat, String lng) {
        coordinates = new String[]{lng,lat};
    }

    public void setLocationPoint(Location location) {

        if (location != null)
            coordinates = new String[]{String.valueOf(location.getLongitude()),String.valueOf(location.getLatitude())};
    }




    protected LocationPoint(Parcel in) {
        coordinates = in.createStringArray();
    }

    public static final Creator<LocationPoint> CREATOR = new Creator<LocationPoint>() {
        @Override
        public LocationPoint createFromParcel(Parcel in) {
            return new LocationPoint(in);
        }

        @Override
        public LocationPoint[] newArray(int size) {
            return new LocationPoint[size];
        }
    };

    public String getLat() {
        return coordinates[1];
    }

    public double getLatdInDuble() {
        return Double.parseDouble(coordinates[1]);
    }

    public double getLngdInDuble() {
        return Double.parseDouble(coordinates[0]);
    }


    public String getLng() {
        return coordinates[0];
    }


    public String[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(coordinates);
    }

}
