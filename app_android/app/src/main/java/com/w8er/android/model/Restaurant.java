package com.w8er.android.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private String restId;
    private String _id;
    private String phone_number;
    private String name;
    private String owner;
    private List<String> tags;
    private List<RestPictures> pictures;
    private boolean kosher;
    private ArrayList<Review> reviews;
    private LocationPoint location;
    private String address;
    private String country;
    private ArrayList<TimeSlot> hours;
    private float rating;
    private String profile_img;
    private MenuItemsId menu;

    public MenuItemsId getMenu() {
        return menu;
    }

    public void setMenu(MenuItemsId menu) {
        this.menu = menu;
    }

    public String getRestId() {
        return restId;
    }

    public void setRestId(String restId) {
        this.restId = restId;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public ArrayList<TimeSlot> getHours() {
        return hours;
    }

    public void setHours(ArrayList<TimeSlot> hours) {
        this.hours = hours;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocationPoint getLocation() {
        return location;
    }

    public void setLocation(LocationPoint location) {
        this.location = location;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<RestPictures> getPictures() {
        return pictures;
    }

    public void setPictures(List<RestPictures> pictures) {
        this.pictures = pictures;
    }

    public boolean isKosher() {
        return kosher;
    }

    public void setKosher(boolean kosher) {
        this.kosher = kosher;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Restaurant)) {
            return false;
        }
        Restaurant u = (Restaurant) other;
        return this.getName().equals(u.getName()) &&
                this.getHours().equals(u.getHours()) &&
                this.getTags().equals(u.getTags());
    }

}