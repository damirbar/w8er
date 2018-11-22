package com.w8er.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ScrollView;

import com.w8er.android.R;
import com.w8er.android.adapters.TimeSlotsAdapter;
import com.w8er.android.model.TimeSlot;
import com.w8er.android.network.ServerResponse;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class OpenHoursActivity extends AppCompatActivity implements TimeSlotsAdapter.ItemClickListener{

    private final int MAX_TIME_SLOTS = 20;
    private final int DEFAULT_OPEN_TIME = 18;
    private final int DEFAULT_CLOSED_TIME = 26;


    private Button mBSave;
    private NumberPicker mTimePickerFrom;
    private NumberPicker mTimePickerTo;
    private NumberPicker mDayPicker;
    private String dayNames[];
    private String hourNames[];
    private ServerResponse mServerResponse;

    private Button mDayButton;
    private Button mFromButton;
    private Button mToButton;
    private Button mAddHoursButton;
    private TimeSlotsAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<TimeSlot> timeSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_hours);
        mServerResponse = new ServerResponse(findViewById(R.id.layout));
        initViews();

        hourNames = new String[]{"00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30","04:00","04:30"
                ,"05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30","09:00","09:30"
                ,"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30","14:00","14:30"
                ,"15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00","19:30","20:00"
                ,"20:30", "21:00", "21:30","22:00", "22:30", "23:00", "23:30"};

        initDayPicker();
        initTimePickerFrom();
        initTimePickerTo();
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
        mAddHoursButton = findViewById(R.id.add_hours_button);
        mAddHoursButton.setOnClickListener(view -> addTimeSlot());
        ScrollView scrollView = findViewById(R.id.scroll_view);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        mBSave = findViewById(R.id.save_button);
        Button mBCancel = findViewById(R.id.cancel_button);
        mBSave.setOnClickListener(view -> saveButton());
        mBCancel.setOnClickListener(view -> finish());
        mTimePickerFrom = findViewById(R.id.timePicker_from);
        mTimePickerTo = findViewById(R.id.timePicker_to);
        mDayPicker = findViewById(R.id.day_picker);
        mDayPicker.setOnValueChangedListener((picker, oldVal, newVal) -> changeDay());
        mTimePickerFrom.setOnValueChangedListener((picker, oldVal, newVal) -> changeHourFrom());
        mTimePickerTo.setOnValueChangedListener((picker, oldVal, newVal) -> changeHourTo());
    }

    private void addTimeSlot() {
        if(timeSlots.size() >= MAX_TIME_SLOTS){
            mServerResponse.showSnackBarMessage("Maximum time slots reached");
            return;
        }
        String hourTo = hourNames[mTimePickerTo.getValue()];
        String hourFrom = hourNames[mTimePickerFrom.getValue()];
        String day = dayNames[mDayPicker.getValue()];

        TimeSlot timeSlot = new TimeSlot(day, hourFrom, hourTo);
        timeSlots.add(timeSlot);
        adapter.notifyDataSetChanged();

        recyclerView.scrollToPosition(timeSlots.size()-1);

        mDayPicker.setValue(mDayPicker.getValue()+1);
        changeDay();
    }

    private void changeHourTo() {
        String hour = hourNames[mTimePickerTo.getValue()];
        mToButton.setText(hour);
    }

    private void changeHourFrom() {
        String hour = hourNames[mTimePickerFrom.getValue()];
        mFromButton.setText(hour);
    }

    private void initTimePickerTo() {
        mTimePickerTo.setMinValue(0);
        mTimePickerTo.setMaxValue(hourNames.length - 1);
        mTimePickerTo.setDisplayedValues(hourNames);
        mTimePickerTo.setValue(DEFAULT_CLOSED_TIME);
    }

    private void initTimePickerFrom() {
        mTimePickerFrom.setMinValue(0);
        mTimePickerFrom.setMaxValue(hourNames.length - 1);
        mTimePickerFrom.setDisplayedValues(hourNames);
        mTimePickerFrom.setValue(DEFAULT_OPEN_TIME);
    }

    private void changeDay() {
        String day = dayNames[mDayPicker.getValue()];
        mDayButton.setText(day);
    }

    private void initDayPicker() {
        dayNames = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        mDayPicker.setMinValue(0);
        mDayPicker.setMaxValue(dayNames.length - 1);
        mDayPicker.setDisplayedValues(dayNames);
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
        if (view.getId() == R.id.textViewRemove) {
            timeSlots.remove(position);
            adapter.notifyDataSetChanged();
        }

    }
}

