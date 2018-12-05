package com.w8er.android.model;

import java.util.List;

public class MenuRest {
    private List<RestItem> appetizer;
    private List<RestItem> main_course;
    private List<RestItem> dessert;
    private List<RestItem> drinks;
    private List<RestItem> deals;
    private List<RestItem> specials;

    public List<RestItem> getAppetizer() {
        return appetizer;
    }

    public void setAppetizer(List<RestItem> appetizer) {
        this.appetizer = appetizer;
    }

    public List<RestItem> getMain_course() {
        return main_course;
    }

    public void setMain_course(List<RestItem> main_course) {
        this.main_course = main_course;
    }

    public List<RestItem> getDessert() {
        return dessert;
    }

    public void setDessert(List<RestItem> dessert) {
        this.dessert = dessert;
    }

    public List<RestItem> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<RestItem> drinks) {
        this.drinks = drinks;
    }

    public List<RestItem> getDeals() {
        return deals;
    }

    public void setDeals(List<RestItem> deals) {
        this.deals = deals;
    }

    public List<RestItem> getSpecials() {
        return specials;
    }

    public void setSpecials(List<RestItem> specials) {
        this.specials = specials;
    }
}
