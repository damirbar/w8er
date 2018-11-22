package com.w8er.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeSlot implements Parcelable {

    private String open;
    private String close;
    private String days;

    public TimeSlot(String day, String hourFrom, String hourTo) {
        this.open = hourFrom;
        this.close = hourTo;
        this.days = day;
    }

    protected TimeSlot(Parcel in) {
        open = in.readString();
        close = in.readString();
        days = in.readString();
    }

    public static final Creator<TimeSlot> CREATOR = new Creator<TimeSlot>() {
        @Override
        public TimeSlot createFromParcel(Parcel in) {
            return new TimeSlot(in);
        }

        @Override
        public TimeSlot[] newArray(int size) {
            return new TimeSlot[size];
        }
    };

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(open);
        dest.writeString(close);
        dest.writeString(days);
    }

    @Override
    public String toString() {
        return days + " " + open + " - " + close;
    }

    public String toStringHours() {
        return open + " - " + close;
    }


    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TimeSlot)) {
            return false;
        }
        TimeSlot u = (TimeSlot) other;
        return this.getDays().equals(u.getDays()) &&
                this.getOpen().equals(u.getOpen()) &&
                this.getClose().equals(u.getClose());
    }

}
