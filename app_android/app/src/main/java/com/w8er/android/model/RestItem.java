package com.w8er.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RestItem implements Parcelable {

    private String _id;
    private String name;
    private String description;
    private double price;
    private boolean available;
    private List<String> tags;
    private String type;
    private String image_url;
    private String image_id;
    private int amount;
    private int cartId;

    public RestItem(){}


    protected RestItem(Parcel in) {
        _id = in.readString();
        name = in.readString();
        description = in.readString();
        price = in.readDouble();
        available = in.readByte() != 0;
        tags = in.createStringArrayList();
        type = in.readString();
        image_url = in.readString();
        image_id = in.readString();
        amount = in.readInt();
        cartId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeByte((byte) (available ? 1 : 0));
        dest.writeStringList(tags);
        dest.writeString(type);
        dest.writeString(image_url);
        dest.writeString(image_id);
        dest.writeInt(amount);
        dest.writeInt(cartId);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RestItem)) {
            return false;
        }
        RestItem u = (RestItem) other;
        return this.getName().equals(u.getName()) &&
                this.get_id().equals(u.get_id()) &&
                this.getDescription().equals(u.getDescription()) &&
                this.getPrice()==(u.getPrice()) &&
                this.getType().equals(u.getType()) &&
                this.getCartId()==(u.getCartId()) &&
                this.getTags().equals(u.getTags());
    }
}
