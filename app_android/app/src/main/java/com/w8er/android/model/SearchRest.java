package com.w8er.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchRest implements Parcelable {

    private String address;
    private String [] tags;
    private LocationPoint location;

    public SearchRest(){}


    protected SearchRest(Parcel in) {
        address = in.readString();
        tags = in.createStringArray();
        location = in.readParcelable(LocationPoint.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeStringArray(tags);
        dest.writeParcelable(location, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchRest> CREATOR = new Creator<SearchRest>() {
        @Override
        public SearchRest createFromParcel(Parcel in) {
            return new SearchRest(in);
        }

        @Override
        public SearchRest[] newArray(int size) {
            return new SearchRest[size];
        }
    };

    public LocationPoint getLocation() {
        return location;
    }

    public void setLocation(LocationPoint location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String [] getTags() {
        return tags;
    }

    public void setTags(String [] tags) {
        this.tags = tags;
    }

    public void setTag(String tag) {
        this.tags = new String[]{tag};
    }

    public String tagsToString() {
        StringBuilder allTags = new StringBuilder();
        for(String s:tags){
            allTags.append(s);
            allTags.append(" ");
        }

        return allTags.toString().trim();
    }


    @Override
    public String toString() {
        return  address + ' ' + tagsToString();
    }

    public void setCurrentLocation() {
        this.address = "Current Location";
    }

}
