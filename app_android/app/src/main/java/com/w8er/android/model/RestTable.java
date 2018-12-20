package com.w8er.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RestTable implements Parcelable {

    private String id;
    private boolean smoking;
    private boolean outside;
    private  int col;
    private  int row;
    private  int amount;


    public RestTable(){ }

    protected RestTable(Parcel in) {
        id = in.readString();
        smoking = in.readByte() != 0;
        outside = in.readByte() != 0;
        col = in.readInt();
        row = in.readInt();
        amount = in.readInt();
    }

    public static final Creator<RestTable> CREATOR = new Creator<RestTable>() {
        @Override
        public RestTable createFromParcel(Parcel in) {
            return new RestTable(in);
        }

        @Override
        public RestTable[] newArray(int size) {
            return new RestTable[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSmoking() {
        return smoking;
    }

    public void setSmoking(boolean smoking) {
        this.smoking = smoking;
    }

    public boolean isOutside() {
        return outside;
    }

    public void setOutside(boolean outside) {
        this.outside = outside;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeByte((byte) (smoking ? 1 : 0));
        dest.writeByte((byte) (outside ? 1 : 0));
        dest.writeInt(col);
        dest.writeInt(row);
        dest.writeInt(amount);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RestTable)) {
            return false;
        }
        RestTable u = (RestTable) other;
        return this.isOutside() == (u.isOutside()) &&
                this.isSmoking() == (u.isSmoking()) &&
                this.getAmount() == (u.getAmount()) &&
                this.getId().equals(u.getId()) &&
                this.getRow() == (u.getRow()) &&
                this.getCol() == (u.getCol());
    }

}
