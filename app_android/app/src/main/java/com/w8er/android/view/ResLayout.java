package com.w8er.android.view;

import android.content.Context;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;


public class ResLayout {


  private int id;
  private int row;
  private int col;
  private ArrayList<Table> tables;

  public ResLayout(Context context, TableLayout layout, int ROW, int COL, ArrayList<String> availableTables, ArrayList<String> bookedTables) {

    TableLayout tableLayout = layout;

    tables = new ArrayList<>();

    for (int i = 0; i < ROW; i++){
      TableRow tableRow = new TableRow(context);

      tableRow.setLayoutParams(new TableLayout.LayoutParams(
              TableLayout.LayoutParams.MATCH_PARENT,
              TableLayout.LayoutParams.MATCH_PARENT, 1.0f
      ));

      tableLayout.addView(tableRow);

      for (int j = 0; j < COL; j++){
        final int FINAL_ROW = i;
        final int FINAL_COL = j;

        final Table table = new Table(context, i, j);
        table.setStatus(false);

        if(availableTables.contains(table.getTableID())){
          table.setAvailable();
          }


        if(bookedTables.contains(table.getTableID())){
          table.setIsBooked();
          }

        table.setBackground();
        table.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT, 1.0f
        ));

        table.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            seatButtonClicked(table, FINAL_ROW, FINAL_COL);
            addSeat(table);

          }

        });

        tableRow.addView(table);
      }
    }
  }

  public ArrayList<Table> getTables(){
    return tables;
  }

  private void seatButtonClicked(Table table, int row, int col) {
    table.setBackground();
  }

  private Boolean addSeat (Table table){
    if (table.IsBooked()){
      return false;
    }

    if(!table.getStatus()){
      tables.add(table);
      return true;

    }else {
      tables.remove(table);
      return false;
    }
  }

}
