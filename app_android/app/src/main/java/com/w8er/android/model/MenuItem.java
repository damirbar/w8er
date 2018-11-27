package com.w8er.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MenuItem implements Parcelable {

    private String name;
    private String description;
    private String price;
    private boolean available;
    private List<String> tags;
    private String picture;
    private String type;

    public MenuItem(){}

    protected MenuItem(Parcel in) {
        name = in.readString();
        description = in.readString();
        price = in.readString();
        available = in.readByte() != 0;
        tags = in.createStringArrayList();
        picture = in.readString();
        type = in.readString();
    }

    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeByte((byte) (available ? 1 : 0));
        dest.writeStringList(tags);
        dest.writeString(picture);
        dest.writeString(type);
    }
}
