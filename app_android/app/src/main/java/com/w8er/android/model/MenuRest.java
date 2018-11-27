package com.w8er.android.model;

import java.util.ArrayList;
import java.util.List;

public class MenuRest {
    private ArrayList<MenuItem> appetizer;
    private ArrayList<MenuItem> main_course;
    private ArrayList<MenuItem> dessert;
    private ArrayList<MenuItem> drinks;
    private ArrayList<MenuItem> deals;
    private ArrayList<MenuItem> specials;

    public ArrayList<MenuItem> getAppetizer() {
        return appetizer;
    }

    public void setAppetizer(ArrayList<MenuItem> appetizer) {
        this.appetizer = appetizer;
    }

    public ArrayList<MenuItem> getMain_course() {
        return main_course;
    }

    public void setMain_course(ArrayList<MenuItem> main_course) {
        this.main_course = main_course;
    }

    public ArrayList<MenuItem> getDessert() {
        return dessert;
    }

    public void setDessert(ArrayList<MenuItem> dessert) {
        this.dessert = dessert;
    }

    public ArrayList<MenuItem> getDrinks() {
        return drinks;
    }

    public void setDrinks(ArrayList<MenuItem> drinks) {
        this.drinks = drinks;
    }

    public ArrayList<MenuItem> getDeals() {
        return deals;
    }

    public void setDeals(ArrayList<MenuItem> deals) {
        this.deals = deals;
    }

    public ArrayList<MenuItem> getSpecials() {
        return specials;
    }

    public void setSpecials(ArrayList<MenuItem> specials) {
        this.specials = specials;
    }
}
