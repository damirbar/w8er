package com.w8er.android.view;

import android.content.Context;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.w8er.android.model.RestTable;

import java.util.ArrayList;


public class ResLayoutView {

    private ArrayList<TableView> tableViews;

    public ResLayoutView(Context context, TableLayout layout, int ROW, int COL, ArrayList<RestTable> tables) {

        tableViews = new ArrayList<>();

        for (int i = 0; i < ROW; i++) {
            TableRow tableRow = new TableRow(context);

            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT, 1.0f
            ));

            layout.addView(tableRow);

            for (int j = 0; j < COL; j++) {

                final TableView table = new TableView(context, i, j);
                table.setStatus(false);


                int pos = tables.indexOf(new RestTable(table.getTableID()));
                if (pos != -1) {
                    table.setAvailable();
                    table.setIsBooked(tables.get(pos).isBooked());
                }

                table.setBackground();
                table.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT, 1.0f
                ));

                table.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        table.setBackground();
                        addSeat(table);
                    }
                });
                tableRow.addView(table);
            }
        }
    }

    private Boolean addSeat(TableView table) {
        if (table.IsBooked()) {
            return false;
        }

        if (!table.getStatus()) {
            clearRestTables();
            tableViews.add(table);
            return true;

        } else {
            tableViews.remove(table);
            return false;
        }
    }

    private void clearRestTables() {
        for (TableView tableView : tableViews) {
            tableView.setBackground();
            tableViews.remove(tableView);
        }
    }
}
