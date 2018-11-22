package com.w8er.android.model;

import java.util.ArrayList;

public class Searchable {

    private ArrayList<Restaurant> restaurants;

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public Searchable() {
        this.restaurants  = new ArrayList<>();
    }
}
