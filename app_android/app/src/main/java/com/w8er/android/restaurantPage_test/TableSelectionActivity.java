package com.w8er.android.restaurantPage_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;

import com.w8er.android.R;
import com.w8er.android.model.RestTable;
import com.w8er.android.view.ResLayoutView;

import java.util.ArrayList;


public class TableSelectionActivity extends AppCompatActivity {

    private static final int ROW = 5;
    private static final int COL = 5;
    private ResLayoutView res;
    private TableLayout tableLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table_selection);
        initViews();
        createLayout();
    }

    private void initViews() {
        tableLayout = findViewById(R.id.tablesLayout);
    }


    private void createLayout() {

        ArrayList<RestTable> tables = new ArrayList<>();
        tables.add(new RestTable("0,0"));
        tables.add(new RestTable("1,0",true));
        tables.add(new RestTable("2,2"));
        tables.add(new RestTable("1,1"));



        res = new ResLayoutView(this, tableLayout, ROW, COL, tables);
    }
}
