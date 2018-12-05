package com.w8er.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RestLayout implements Parcelable {
    private ArrayList<RestTable> tables;
    private  int row;
    private  int col;

    public RestLayout(){}

    protected RestLayout(Parcel in) {
        tables = in.createTypedArrayList(RestTable.CREATOR);
        row = in.readInt();
        col = in.readInt();
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

    public ArrayList<RestTable> getTables() {
        return tables;
    }

    public void setTables(ArrayList<RestTable> tables) {
        this.tables = tables;
    }

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
    public boolean equals(Object other) {
        if (!(other instanceof RestLayout)) {
            return false;
        }
        RestLayout u = (RestLayout) other;
        return this.getTables().equals(u.getTables()) &&
                this.getRow() == (u.getRow()) &&
                this.getCol() == (u.getCol());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(tables);
        dest.writeInt(row);
        dest.writeInt(col);
    }
}
