package com.w8er.android.model;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.w8er.android.R;


public class Seat extends android.support.v7.widget.AppCompatButton {
  private String ID; //hashcode //row col string //

  private int roomID;
  private String seatID;
  private int rowNum;
  private int colNum;
  private boolean isSelected;
  private boolean isAvailable;
  private boolean isBooked = false;
  private final double price = 10.00;


  private static final String TAG = "Seat";


  public Seat(Context context) {
    super(context);
  }

  public Seat(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public Seat(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public Seat(Context context, int row, int col) {
    super(context);
    this.rowNum = row;
    this.colNum = col;
    this.seatID = Integer.toString(this.rowNum) + "," + Integer.toString(this.colNum);
//        Log.d(TAG, "Seat: ----------------------------- ID = " + seatID);
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
      Log.d(TAG, "setBackground: isbooked " + isBooked + " set this chair to booked");
      this.setBackgroundResource(R.drawable.seat_sold);
      return false;
    }
    if(this.isSelected) {
      Log.d(TAG, "setBackground: Status was true");
      this.setBackgroundResource(R.drawable.seat_selected);
      this.isSelected = false;
      return true;
    }
    if(this.isAvailable) {
        Log.d(TAG, "setBackground: Status was true");
        this.setBackgroundResource(R.drawable.seat_sale);
        this.isSelected = true;
        return true;
    }else{
      this.setVisibility(INVISIBLE);
//      this.isSelected = true;
      return false;
    }


  }

  public void setStatus(boolean status) {
    this.isSelected = status;
  }

  public boolean getStatus() {
    return isSelected;
  }

  public double getPrice() {
    return price;
  }

  public String getSeatID(){
    return this.seatID;
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