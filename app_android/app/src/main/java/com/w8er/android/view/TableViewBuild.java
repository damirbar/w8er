package com.w8er.android.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;

import com.w8er.android.R;
import com.w8er.android.dialogs.CountryDialog;
import com.w8er.android.dialogs.ViewItemDialog;

public class TableViewBuild extends android.support.v7.widget.AppCompatButton {
    private String tableID;
    private String type;
    private boolean isSelected;
//    private boolean isPlace = false;

    public TableViewBuild(Context context) {
        super(context);
    }

    public TableViewBuild(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TableViewBuild(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TableViewBuild(Context context, int row, int col) {
        super(context);
        this.tableID = Integer.toString(row) + "," + Integer.toString(col);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBackground(String type) {

        this.setScaleX(0.6f);
//        this.setScaleY(0.8f);

        switch(type)
        {
            case "Exit":
                this.setBackgroundResource(R.drawable.ic_exit);
                break;
            case "Restroom":
                this.setBackgroundResource(R.drawable.ic_restroom_sign);
                break;
            case "Table":
                this.setBackgroundResource(R.drawable.ic_table);
                break;
            case "Remove":
                this.setBackgroundResource(R.drawable.ic_box_empty);
                break;
            case "Bar":
                this.setBackgroundResource(R.drawable.ic_bar);
                break;
            default:
                this.setBackgroundResource(R.drawable.ic_box_empty);
        }


//        if (this.isPlace) {
//            this.setBackgroundResource(R.drawable.ic_table);
//            this.isPlace = false;
//            return false;
//        }
//        if (this.isSelected) {
//            this.setBackgroundResource(R.drawable.ic_table);
//            this.isSelected = false;
//            return false;
//        } else {
//            this.setBackgroundResource(R.drawable.ic_question_mark);
//            this.isSelected = true;
//            return false;
//        }

    }

//    public void setStatus(boolean status) {
//        this.isSelected = status;
//    }
//
//    public void setPlace(boolean place) {
//        isPlace = place;
//    }
//
    public boolean getStatus() {
        return isSelected;
    }


    public String getTableID() {
        return this.tableID;
    }

}