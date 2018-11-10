package com.w8er.android.restaurantPage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;

import com.w8er.android.R;
import com.w8er.android.view.ResLayout;

import java.util.ArrayList;


public class TableSelectionActivity extends AppCompatActivity {

    private static final int ROW = 5;
    private static final int COL = 5;
    private ResLayout res;
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

        ArrayList<String> booked = new ArrayList<String>();
        booked.add(Integer.toString(0) + "," + Integer.toString(0));
        booked.add(Integer.toString(4) + "," + Integer.toString(2));
        booked.add(Integer.toString(4) + "," + Integer.toString(3));


        ArrayList<String> available = new ArrayList<String>();
        available.add(Integer.toString(0) + "," + Integer.toString(0));
        available.add(Integer.toString(0) + "," + Integer.toString(1));
        available.add(Integer.toString(0) + "," + Integer.toString(2));
        available.add(Integer.toString(1) + "," + Integer.toString(0));
        available.add(Integer.toString(1) + "," + Integer.toString(1));
        available.add(Integer.toString(1) + "," + Integer.toString(2));
        available.add(Integer.toString(4) + "," + Integer.toString(0));
        available.add(Integer.toString(4) + "," + Integer.toString(1));
        available.add(Integer.toString(4) + "," + Integer.toString(2));
        available.add(Integer.toString(4) + "," + Integer.toString(3));
        available.add(Integer.toString(0) + "," + Integer.toString(4));

        res = new ResLayout(this, tableLayout, ROW, COL, available, booked);
    }
}
