package com.w8er.android.model;

import java.util.ArrayList;

public class MenuRest {
    private ArrayList<RestItem> appetizer;
    private ArrayList<RestItem> main_course;
    private ArrayList<RestItem> dessert;
    private ArrayList<RestItem> drinks;
    private ArrayList<RestItem> deals;
    private ArrayList<RestItem> specials;

    public ArrayList<RestItem> getAppetizer() {
        return appetizer;
    }

    public void setAppetizer(ArrayList<RestItem> appetizer) {
        this.appetizer = appetizer;
    }

    public ArrayList<RestItem> getMain_course() {
        return main_course;
    }

    public void setMain_course(ArrayList<RestItem> main_course) {
        this.main_course = main_course;
    }

    public ArrayList<RestItem> getDessert() {
        return dessert;
    }

    public void setDessert(ArrayList<RestItem> dessert) {
        this.dessert = dessert;
    }

    public ArrayList<RestItem> getDrinks() {
        return drinks;
    }

    public void setDrinks(ArrayList<RestItem> drinks) {
        this.drinks = drinks;
    }

    public ArrayList<RestItem> getDeals() {
        return deals;
    }

    public void setDeals(ArrayList<RestItem> deals) {
        this.deals = deals;
    }

    public ArrayList<RestItem> getSpecials() {
        return specials;
    }

    public void setSpecials(ArrayList<RestItem> specials) {
        this.specials = specials;
    }
}
