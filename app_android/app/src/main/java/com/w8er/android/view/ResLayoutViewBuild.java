package com.w8er.android.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.w8er.android.dialogs.ViewItemDialog;
import com.w8er.android.model.RestTable;

import java.util.ArrayList;


public class ResLayoutViewBuild {

    private ArrayList<TableViewBuild> tableViews;
    private int ROW;
    private int COL;
    private Context context;

    public int getROW() {
        return ROW;
    }

    public int getCOL() {
        return COL;
    }

    public ArrayList<RestTable> getTables() {
        ArrayList<RestTable> restTables = new ArrayList<>();

        for(TableViewBuild t :tableViews ){
            RestTable table = new RestTable();
            table.setTableId(t.getTableID());
            restTables.add(table);
        }
        return restTables;
    }

    public ResLayoutViewBuild(Context _Context, TableLayout layout, int _ROW, int _COL, ArrayList<RestTable> tables) {
        context = _Context;
        tableViews = new ArrayList<>();
        ROW = _ROW;
        COL = _COL;

        for (int i = 0; i < ROW; i++) {
            TableRow tableRow = new TableRow(context);
            TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT, 1.0f
            );
            params.setMargins(0, 0, 0, 25);
            tableRow.setLayoutParams(params);
            layout.addView(tableRow);


            for (int j = 0; j < COL; j++) {

                final TableViewBuild table = new TableViewBuild(context, i, j);
                table.setStatus(false);

                int pos = tables.indexOf(new RestTable(table.getTableID()));
                if (pos != -1) {
                    table.setPlace(true);
                    tableViews.add(table);
                }

                table.setBackground();
                table.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT, 1.0f
                ));

                table.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemDialog();
                    }
                });
                tableRow.addView(table);
            }
        }
    }

    private Boolean addSeat(TableViewBuild table) {
        if (!table.getStatus()) {

            tableViews.add(table);
            return true;

        } else {
            tableViews.remove(table);
            return false;
        }
    }

    public void itemDialog() {
        ViewItemDialog newFragment = new ViewItemDialog();
//        String c = mETCountry.getText().toString().trim();
//        if (!(c.isEmpty())) {
//            Bundle bundle = new Bundle();
//            bundle.putString("country", c);
//            newFragment.setArguments(bundle);
//        }
        newFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), ViewItemDialog.TAG);

    }

}
