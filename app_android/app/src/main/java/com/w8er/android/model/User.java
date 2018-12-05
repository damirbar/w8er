package com.w8er.android.model;


import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class User {
    private String _id;
    private String first_name;
    private String phone_number;
    private String last_name;
    private String email;
    private String accessToken;
    private String password;
    private String about_me;
    private String type;
    private Date date;
    private String profile_img;
    private List<String> favorite_foods;
    private List<String> favorite_restaurants;
    private String new_password;
    private boolean is_admin;
    private String gender;
    private Date birthday;
    private String address;
    private String country;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public List<String> getFavorite_foods() {
        return favorite_foods;
    }

    public void setFavorite_foods(List<String> favorite_foods) {
        this.favorite_foods = favorite_foods;
    }

    public List<String> getFavorite_restaurants() {
        return favorite_restaurants;
    }

    public void setFavorite_restaurants(List<String> favorite_restaurants) {
        this.favorite_restaurants = favorite_restaurants;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof User)) {
            return false;
        }
        User u = (User) other;

        Boolean date = true;
        if ((this.getBirthday() != null && u.getBirthday() == null) || (this.getBirthday() == null && u.getBirthday() != null)){
            return false;
        }

        if (this.getBirthday() != null && u.getBirthday() != null) {
            Format formatter = new SimpleDateFormat("d MMM yyyy");
            String s1 = formatter.format(this.getBirthday());
            String s2 = formatter.format(u.getBirthday());
            if(!s1.equals(s2)){
                date = false;
            }
        }
        return  date &&
                this.getFirst_name().equals(u.getFirst_name()) &&
                this.getLast_name().equals(u.getLast_name()) &&
                this.getCountry().equals(u.getCountry()) &&
                this.getAddress().equals(u.getAddress()) &&
                this.getGender().equals(u.getGender()) &&
                this.getAbout_me().equals(u.getAbout_me());
    }

}


