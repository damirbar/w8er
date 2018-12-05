package com.w8er.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RestTable implements Parcelable {

    private String tableId;
    private boolean booked;

    public RestTable(){ }

    public RestTable(String _tableID) {
        tableId = _tableID;
        booked = false;
    }

    public RestTable(String _tableID ,boolean _booked) {
        tableId = _tableID;
        booked = _booked;
    }


    protected RestTable(Parcel in) {
        tableId = in.readString();
        booked = in.readByte() != 0;
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

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RestTable)) {
            return false;
        }
        RestTable u = (RestTable) other;
        return this.getTableId().equals(u.getTableId());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tableId);
        dest.writeByte((byte) (booked ? 1 : 0));
    }
}
