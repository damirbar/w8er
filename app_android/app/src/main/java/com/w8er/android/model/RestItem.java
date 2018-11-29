package com.w8er.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RestItem implements Parcelable {

    private String _id;
    private String name;
    private String description;
    private String price;
    private boolean available;
    private List<String> tags;
    private String picture;
    private String type;

    public RestItem(){}

    protected RestItem(Parcel in) {
        _id = in.readString();
        name = in.readString();
        description = in.readString();
        price = in.readString();
        available = in.readByte() != 0;
        tags = in.createStringArrayList();
        picture = in.readString();
        type = in.readString();
    }

    public static final Creator<RestItem> CREATOR = new Creator<RestItem>() {
        @Override
        public RestItem createFromParcel(Parcel in) {
            return new RestItem(in);
        }

        @Override
        public RestItem[] newArray(int size) {
            return new RestItem[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

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
    public boolean equals(Object other) {
        if (!(other instanceof RestItem)) {
            return false;
        }
        RestItem u = (RestItem) other;
        return this.getName().equals(u.getName()) &&
                this.getDescription().equals(u.getDescription()) &&
                this.getPrice().equals(u.getPrice()) &&
                this.getType().equals(u.getType()) &&
                this.getTags().equals(u.getTags());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeByte((byte) (available ? 1 : 0));
        dest.writeStringList(tags);
        dest.writeString(picture);
        dest.writeString(type);
    }
}
