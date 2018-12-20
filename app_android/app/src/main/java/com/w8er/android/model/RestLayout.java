package com.w8er.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RestLayout implements Parcelable {
    private ArrayList<RestTable> tables;
    private ArrayList<StaticItem> staticItems;
    private  Grid grid;

    public RestLayout(){}


    protected RestLayout(Parcel in) {
        tables = in.createTypedArrayList(RestTable.CREATOR);
        staticItems = in.createTypedArrayList(StaticItem.CREATOR);
        grid = in.readParcelable(Grid.class.getClassLoader());
    }

    public static final Creator<RestLayout> CREATOR = new Creator<RestLayout>() {
        @Override
        public RestLayout createFromParcel(Parcel in) {
            return new RestLayout(in);
        }

        @Override
        public RestLayout[] newArray(int size) {
            return new RestLayout[size];
        }
    };

    public ArrayList<StaticItem> getStaticItems() {
        return staticItems;
    }

    public void setStaticItems(ArrayList<StaticItem> staticItems) {
        this.staticItems = staticItems;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public ArrayList<RestTable> getTables() {
        return tables;
    }

    public void setTables(ArrayList<RestTable> tables) {
        this.tables = tables;
    }

    public int getRow() {
        return grid.getRow();
    }

    public void setRow(int row) {
        this.grid.setRow(row);
    }

    public int getCol() {
        return grid.getCol();
    }

    public void setCol(int col) {
        this.grid.setCol(col);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RestLayout)) {
            return false;
        }
        RestLayout u = (RestLayout) other;
        return this.getStaticItems().equals(u.getStaticItems()) &&
                this.getTables().equals(u.getTables()) &&
                this.getGrid().equals(u.getGrid());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(tables);
        dest.writeTypedList(staticItems);
        dest.writeParcelable(grid, flags);
    }
}
