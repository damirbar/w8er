package com.w8er.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;

import com.w8er.android.R;
import com.w8er.android.dialogs.NumberDialog;
import com.w8er.android.dialogs.ViewItemDialog;
import com.w8er.android.model.RestLayout;
import com.w8er.android.model.RestTable;
import com.w8er.android.view.ResLayoutViewBuild;

import java.util.ArrayList;

public class EditRestLayoutActivity extends AppCompatActivity implements NumberDialog.OnCallbackNum, ViewItemDialog.OnCallbackItem {

    private ResLayoutViewBuild res;
    private TableLayout tableLayout;
    private Button mFromButton;
    private Button mToButton;
    private int saveNumFrom;
    private int saveNumTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_layout);
        initViews();
        getData();




    }

    private boolean getData() {
        if (getIntent().getExtras() != null) {
            RestLayout layout = getIntent().getExtras().getParcelable("layout");
            if (layout != null) {
                createLayout(layout.getTables(), layout.getRow(), layout.getCol());
                saveNumFrom = layout.getRow();
                saveNumTo = layout.getCol();
                mFromButton.setText(String.valueOf(saveNumFrom));
                mToButton.setText(String.valueOf(saveNumTo));
                return true;
            } else
                return false;
        } else
            return false;
    }

    private void initViews() {
        final HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.scroll);

        scrollView.postDelayed(new Runnable() {
            public void run() {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 100L);

        tableLayout = findViewById(R.id.tablesLayout);
        mFromButton = findViewById(R.id.from_button);
        mToButton = findViewById(R.id.to_button);
        mFromButton.setOnClickListener(view -> viewSizeClick(0));
        mToButton.setOnClickListener(view -> viewSizeClick(1));
        Button mSetLayoutButton = findViewById(R.id.set_layout_size);
        mSetLayoutButton.setOnClickListener(view -> setLayout());
        Button mClearLayoutButton = findViewById(R.id.clear_layout);
        mClearLayoutButton.setOnClickListener(view -> clearLayout());
        Button mBSave = findViewById(R.id.save_button);
        Button mBCancel = findViewById(R.id.cancel_button);
        mBSave.setOnClickListener(view -> saveButton());
        mBCancel.setOnClickListener(view -> finish());
    }

    private void clearLayout() {
        tableLayout.removeAllViews();

        ArrayList<RestTable> tables = new ArrayList<>();
        createLayout(tables, saveNumFrom, saveNumTo);
    }

    public void saveButton() {

        Intent i = new Intent();
        Bundle extra = new Bundle();
        RestLayout restLayout = new RestLayout();
        restLayout.setRow(res.getROW());
        restLayout.setCol(res.getCOL());
        restLayout.setTables(res.getTables());

        extra.putParcelable("layout", restLayout);
        i.putExtras(extra);
        setResult(Activity.RESULT_OK, i);
        finish();
    }


    private void setLayout() {
        ArrayList<RestTable> tables = new ArrayList<>();
        if (res != null) {
            tables = res.getTables();
        }

        tableLayout.removeAllViews();
        String numTo = mToButton.getText().toString().trim();
        String numFrom = mFromButton.getText().toString().trim();

        saveNumFrom = Integer.valueOf(numFrom);
        saveNumTo = Integer.valueOf(numTo);
        createLayout(tables, saveNumFrom, saveNumTo);
    }

    public void viewSizeClick(int id) {
        NumberDialog newFragment = new NumberDialog();
        String h;
        if (id == 0)
            h = mFromButton.getText().toString().trim();
        else
            h = mToButton.getText().toString().trim();

        if (!(h.isEmpty())) {
            Bundle bundle = new Bundle();
            bundle.putString("num", h);
            bundle.putInt("id", id);
            newFragment.setArguments(bundle);
        }
        newFragment.show(getSupportFragmentManager(), NumberDialog.TAG);
    }

    private void createLayout(ArrayList<RestTable> tables, int ROW, int COL) {

        res = new ResLayoutViewBuild(this, tableLayout, ROW, COL, tables);
    }

    @Override
    public void UpdateNum(String num, int id) {
        if (id == 0)
            mFromButton.setText(num);
        else
            mToButton.setText(num);
    }

    @Override
    public void UpdateItem(String item) {
        res.setItem(item);
    }
}
