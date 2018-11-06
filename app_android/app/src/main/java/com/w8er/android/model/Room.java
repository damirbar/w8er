package com.w8er.android.model;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;


public class Room {

  private static final String TAG = "Room";

  private int id;
  private int row;
  private int col;
  private ArrayList<Seat> seats;

  public Room(Context context, TableLayout layout, int ROW, int COL, ArrayList<String> availableSeats, ArrayList<String> bookedseats) {
//
    if (bookedseats.size() != 0) {
      Log.d(TAG, "Room: ----------------------------------------------- " + bookedseats.get(0));
    }

    TableLayout tableLayout = layout;
//    final TextView titleTextView = textView;

    seats = new ArrayList<>();

    for (int i = 0; i < ROW; i++){
      TableRow tableRow = new TableRow(context);



      tableRow.setLayoutParams(new TableLayout.LayoutParams(
              TableLayout.LayoutParams.MATCH_PARENT,
              TableLayout.LayoutParams.MATCH_PARENT, 1.0f
      ));

//      TableLayout.LayoutParams tableParams=
//              new TableLayout.LayoutParams
//                      (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT,1.0f);
//
//      tableParams.setMargins(0, 0, 20, 20);


      tableLayout.addView(tableRow);

      for (int j = 0; j < COL; j++){
        final int FINAL_ROW = i;
        final int FINAL_COL = j;

        final Seat seat = new Seat(context, i, j);
        seat.setStatus(false);

        if(availableSeats.contains(seat.getSeatID())){
          seat.setAvailable();

          Log.d(TAG, "Room: ------------------------------- " + seat.getSeatID());
        }


        if(bookedseats.contains(seat.getSeatID())){
          seat.setIsBooked();

          Log.d(TAG, "Room: ------------------------------- " + seat.getSeatID());
        }



        seat.setBackground();
        seat.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT, 1.0f
        ));

        seat.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            seatButtonClicked(seat, FINAL_ROW, FINAL_COL);
            addSeat(seat);

//            System.out.println("size---------- " + seats.size());
//            System.out.println(calculate(seats));
//            String result = Double.toString(calculate(seats));
//            titleTextView.setText("Totaal: " + result);

          }

        });




        tableRow.addView(seat);
      }
    }
  }

  public ArrayList<Seat> getSeats(){
    return seats;
  }

  private void seatButtonClicked(Seat seat, int row, int col) {
//        Toast.makeText(this, "Button clicked " + row + " " + col, Toast.LENGTH_SHORT).show();
    seat.setBackground();
  }

  private Boolean addSeat (Seat seat){
    if (seat.IsBooked()){
      return false;
    }

    if(!seat.getStatus()){
      seats.add(seat);
      return true;

    }else {
      seats.remove(seat);
      return false;
    }
  }

//  public double calculate(ArrayList<Seat>seats){
//    double price = 0;
//    for(Seat seat: seats){
//      price += seat.getPrice();
//    }
//    return price;
//  }


}
