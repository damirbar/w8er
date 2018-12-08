package com.w8er.android.ui.views;

import android.content.Context;
import android.util.AttributeSet;

import com.w8er.android.R;

public class TableView extends android.support.v7.widget.AppCompatButton {
    private String tableID;
    private boolean isSelected;
    private boolean isAvailable;
    private boolean isBooked = false;

    public TableView(Context context) {
        super(context);
    }

    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TableView(Context context, int row, int col) {
        super(context);
        this.tableID = Integer.toString(row) + "," + Integer.toString(col);
    }

    public boolean setBackground() {

        this.setScaleX(0.9f);
        this.setScaleY(0.9f);

        if (this.isBooked) {
            this.setBackgroundResource(R.drawable.table_close);
            return false;
        }
        if (this.isSelected) {
            this.setBackgroundResource(R.drawable.table_selected);
            this.isSelected = false;
            return false;
        }
        if (this.isAvailable) {
            this.setBackgroundResource(R.drawable.table_open);
            this.isSelected = true;
            return true;
        } else {
            this.setVisibility(INVISIBLE);
            return false;
        }
    }

    public void setStatus(boolean status) {
        this.isSelected = status;
    }

    public boolean getStatus() {
        return isSelected;
    }


    public String getTableID() {
        return this.tableID;
    }

    public void setIsBooked() {
        this.isBooked = true;
    }

    public boolean IsBooked() {
        return this.isBooked;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable() {
        this.isAvailable = true;
    }

    public void setIsBooked(boolean booked) {
        isBooked = booked;
    }
}