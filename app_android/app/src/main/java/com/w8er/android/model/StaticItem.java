package com.w8er.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StaticItem implements Parcelable {

    private String type;
    private  int row;
    private  int col;

    public StaticItem(){}

    public StaticItem(String type, int row, int col) {
        this.type = type;
        this.row = row;
        this.col = col;
    }

    protected StaticItem(Parcel in) {
        type = in.readString();
        row = in.readInt();
        col = in.readInt();
    }

    public static final Creator<StaticItem> CREATOR = new Creator<StaticItem>() {
        @Override
        public StaticItem createFromParcel(Parcel in) {
            return new StaticItem(in);
        }

        @Override
        public StaticItem[] newArray(int size) {
            return new StaticItem[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeInt(row);
        dest.writeInt(col);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StaticItem)) {
            return false;
        }
        StaticItem u = (StaticItem) other;
        return this.getType().equals(u.getType()) &&
                this.getRow() == (u.getRow()) &&
                this.getCol() == (u.getCol());
    }

}
