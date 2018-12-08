package com.w8er.android.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.w8er.android.R;
import com.w8er.android.adapters.TimeSlotsAdapter;
import com.w8er.android.ui.dialogs.DayDialog;
import com.w8er.android.ui.dialogs.HourDialog;
import com.w8er.android.model.TimeSlot;
import com.w8er.android.network.ServerResponse;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class OpenHoursActivity extends AppCompatActivity implements TimeSlotsAdapter.ItemClickListener ,DayDialog.OnCallbackDay, HourDialog.OnCallbackHour {

    private final int MAX_TIME_SLOTS = 20;

    private ServerResponse mServerResponse;
    private Button mDayButton;
    private Button mFromButton;
    private Button mToButton;
    private TimeSlotsAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<TimeSlot> timeSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_hours);
        mServerResponse = new ServerResponse(findViewById(R.id.layout));
        initViews();
        getData();
    }

    private void initTimeSlots() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TimeSlotsAdapter(this, timeSlots);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.rvRes);
        mDayButton = findViewById(R.id.day_button);
        mFromButton = findViewById(R.id.from_button);
        mToButton = findViewById(R.id.to_button);
        Button mAddHoursButton = findViewById(R.id.add_hours_button);
        mAddHoursButton.setOnClickListener(view -> addTimeSlot());
        ScrollView scrollView = findViewById(R.id.scroll_view);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        Button mBSave = findViewById(R.id.save_button);
        Button mBCancel = findViewById(R.id.cancel_button);
        mBSave.setOnClickListener(view -> saveButton());
        mBCancel.setOnClickListener(view -> finish());
        mDayButton.setOnClickListener(view -> dayViewClick());
        mFromButton.setOnClickListener(view -> HourViewClick(0));
        mToButton.setOnClickListener(view -> HourViewClick(1));

    }

    private void addTimeSlot() {
        if(timeSlots.size() >= MAX_TIME_SLOTS){
            mServerResponse.showSnackBarMessage("Maximum time slots reached");
            return;
        }
        String hourTo = mToButton.getText().toString().trim();
        String hourFrom = mFromButton.getText().toString().trim();
        String day = mDayButton.getText().toString().trim();

        TimeSlot timeSlot = new TimeSlot(day, hourFrom, hourTo);
        adapter.getmData().add(0,timeSlot);
        adapter.notifyDataSetChanged();

        recyclerView.scrollToPosition(timeSlots.size()-1);

    }


    private void getData() {
        if (getIntent().getExtras() != null) {
            timeSlots =  getIntent().getExtras().getParcelableArrayList("timeSlots");
            if(timeSlots!=null){
                initTimeSlots();
                adapter.notifyDataSetChanged();
            }
            else{
                timeSlots = new ArrayList<>();
                initTimeSlots();
            }
        }
    }


    public void saveButton() {
        Intent i = new Intent();
        Bundle extra = new Bundle();
        extra.putParcelableArrayList("timeSlots", timeSlots);
        i.putExtras(extra);
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    @Override
    public void onItemClick(View view, int position) {
        try {
            if (view.getId() == R.id.textViewRemove && position < adapter.getmData().size()) {
                adapter.getmData().remove(position);
                adapter.notifyDataSetChanged();
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }

    public void dayViewClick() {
        DayDialog newFragment = new DayDialog();
        String d = mDayButton.getText().toString().trim();
        if (!(d.isEmpty())) {
            Bundle bundle = new Bundle();
            bundle.putString("day", d);
            newFragment.setArguments(bundle);
        }
        newFragment.show(getSupportFragmentManager(), DayDialog.TAG);
    }

    public void HourViewClick(int id) {
        HourDialog newFragment = new HourDialog();
        String h;
        if(id == 0)
            h = mFromButton.getText().toString().trim();
        else
            h = mToButton.getText().toString().trim();

        if (!(h.isEmpty())) {
            Bundle bundle = new Bundle();
            bundle.putString("hour", h);
            bundle.putInt("id", id);
            newFragment.setArguments(bundle);
        }
        newFragment.show(getSupportFragmentManager(), HourDialog.TAG);
    }


    @Override
    public void UpdateDay(String day) {
        mDayButton.setText(day);

    }

    @Override
    public void UpdateHour(String hour, int id) {
        if(id == 0)
            mFromButton.setText(hour);
        else
            mToButton.setText(hour);

    }

}

