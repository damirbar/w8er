package com.w8er.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class SearchRest implements Parcelable {

    private String address;
    private String [] tags;

    public SearchRest(){}

    protected SearchRest(Parcel in) {
        address = in.readString();
        tags = in.createStringArray();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeStringArray(tags);
    }

    @Override
    public String toString() {
        StringBuilder allTags = new StringBuilder();
        for(String s:tags){
            allTags.append(s);
            allTags.append(" ");
        }

        return  address + ' ' + allTags.toString().trim();
    }
}
