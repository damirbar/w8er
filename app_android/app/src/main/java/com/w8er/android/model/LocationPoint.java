package com.w8er.android.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class LocationPoint implements Parcelable {
    private double[] coordinates;

    public LocationPoint() {
    }

    public LocationPoint(String lat, String lng) {
        coordinates = new double[]{Double.parseDouble(
                lng),Double.parseDouble(
                lat)};
    }

    public LocationPoint(double lat, double lng) {
        coordinates = new double[]{lng,lat};
    }


    public LocationPoint(Location location) {

        if (location != null)
            coordinates = new double[]{location.getLongitude(),location.getLatitude()};
    }


    protected LocationPoint(Parcel in) {
        coordinates = in.createDoubleArray();
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

    public void setLocationPoint(LatLng latLng) {
        if (latLng != null)
            coordinates = new double[]{latLng.longitude,latLng.latitude};

    }


    public void setLocationPoint(String lat, String lng) {
        coordinates = new double[]{Double.parseDouble(
                lng),Double.parseDouble(
                lat)};
    }

    public void setLocationPoint(Location location) {

        if (location != null)
            coordinates = new double[]{location.getLongitude(),location.getLatitude()};
    }


    public double getLat() {
        return coordinates[1];
    }

    public double getLng() {
        return coordinates[0];
    }


    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDoubleArray(coordinates);
    }
}
