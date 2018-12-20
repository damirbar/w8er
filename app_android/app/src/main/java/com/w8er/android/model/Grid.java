package com.w8er.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Grid implements Parcelable {

    private  int row;
    private  int col;

    public Grid(){}

    public Grid(int row, int col) {
        this.row = row;
        this.col = col;
    }

    protected Grid(Parcel in) {
        row = in.readInt();
        col = in.readInt();
    }

    public static final Creator<Grid> CREATOR = new Creator<Grid>() {
        @Override
        public Grid createFromParcel(Parcel in) {
            return new Grid(in);
        }

        @Override
        public Grid[] newArray(int size) {
            return new Grid[size];
        }
    };

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(row);
        dest.writeInt(col);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Grid)) {
            return false;
        }
        Grid u = (Grid) other;
        return this.getRow() == (u.getRow()) &&
                this.getCol() == (u.getCol());
    }

}
