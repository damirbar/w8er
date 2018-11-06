package com.w8er.android.view;

import android.content.Context;
import android.util.AttributeSet;

import com.w8er.android.R;


public class Table extends android.support.v7.widget.AppCompatButton {
  private String ID;
  private String tableID;
  private int rowNum;
  private int colNum;
  private boolean isSelected;
  private boolean isAvailable;
  private boolean isBooked = false;

  public Table(Context context) {
    super(context);
  }

  public Table(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public Table(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public Table(Context context, int row, int col) {
    super(context);
    this.rowNum = row;
    this.colNum = col;
    this.tableID = Integer.toString(this.rowNum) + "," + Integer.toString(this.colNum);
  }


  public int getRowNum() {
    return rowNum;
  }

  public void setRowNum(int rowNum) {
    this.rowNum = rowNum;
  }

  public int getColNum() {
    return colNum;
  }

  public void setColNum(int colNum) {
    this.colNum = colNum;
  }

  public boolean setBackground(){

    this.setScaleX(0.9f);
    this.setScaleY(0.9f);


    if (this.isBooked) {
      this.setBackgroundResource(R.drawable.table_close);
      return false;
    }
    if(this.isSelected) {
      this.setBackgroundResource(R.drawable.table_selected);
      this.isSelected = false;
      return true;
    }
    if(this.isAvailable) {
        this.setBackgroundResource(R.drawable.table_open);
        this.isSelected = true;
        return true;
    }else{
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


  public String getTableID(){
    return this.tableID;
  }

  public void setIsBooked(){
    this.isBooked = true;
  }

  public boolean IsBooked(){
    return this.isBooked;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable() {
    this.isAvailable = true;
  }
}