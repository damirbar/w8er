package com.w8er.android.ui.views;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.w8er.android.ui.dialogs.ViewItemDialog;
import com.w8er.android.model.RestTable;

import java.util.ArrayList;

public class ResLayoutViewBuild {

    private ArrayList<TableViewBuild> tableViews;
    private int ROW;
    private int COL;
    private Context context;
    private TableViewBuild tableViewSelect;

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
            params.setMargins(0, 25, 0, 25);
            tableRow.setLayoutParams(params);
            layout.addView(tableRow);

            for (int j = 0; j < COL; j++) {

                TableViewBuild tableView = new TableViewBuild(context, i, j);
//                tableView.setStatus(false);

                int pos = tables.indexOf(new RestTable(tableView.getTableID()));
                if (pos != -1) {
//                    tableView.setPlace(true);
                    tableViews.add(tableView);
                }

                tableView.setBackground("");
                tableView.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT, 1.0f
                ));

                tableView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tableViewSelect = tableView;
                        itemDialog();
                    }
                });
                tableRow.addView(tableView);
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

    private void itemDialog() {
        ViewItemDialog newFragment = new ViewItemDialog();
        newFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), ViewItemDialog.TAG);
    }

    public void setItem(String type) {
        tableViewSelect.setType(type);
        tableViewSelect.setBackground(type);
        addSeat(tableViewSelect);
    }

    public int getROW() {
        return ROW;
    }

    public int getCOL() {
        return COL;
    }

    public ArrayList<RestTable> getTables() {
        ArrayList<RestTable> restTables = new ArrayList<>();

        for (TableViewBuild t : tableViews) {
            RestTable table = new RestTable();
            table.setTableId(t.getTableID());
            restTables.add(table);
        }
        return restTables;
    }
}

