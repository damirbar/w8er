package com.w8er.android.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TimePicker;

import com.w8er.android.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class OpenHoursActivity extends AppCompatActivity {

    private final static int TIME_PICKER_INTERVAL = 30;
    private Button mBSave;
    private TimePicker mTimePicker;
    private NumberPicker mNumberPicker;
    private String dayNames[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_hours);
        initViews();
        initDayPicker();
        getData();
    }

    private void initViews() {
        mBSave = findViewById(R.id.save_button);
        Button mBCancel = findViewById(R.id.cancel_button);
        mBSave.setOnClickListener(view -> saveButton());
        mBCancel.setOnClickListener(view -> finish());
        mTimePicker = findViewById(R.id.timePicker);
        mNumberPicker = findViewById(R.id.number_picker);

        setTimePickerInterval(mTimePicker);

    }

    private void setTimePickerInterval(TimePicker timePicker) {
        try {
            timePicker.setIs24HourView(true);
            NumberPicker minutePicker = (NumberPicker) timePicker.findViewById(Resources.getSystem().getIdentifier(
                    "minute", "id", "android"));
            minutePicker.setMinValue(0);
            minutePicker.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
            List<String> displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            minutePicker.setDisplayedValues(displayedValues.toArray(new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initDayPicker() {
        dayNames = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(dayNames.length - 1);
        mNumberPicker.setDisplayedValues(dayNames);
    }



    private void getData() {
//        if (getIntent().getExtras() != null) {
//            String _bio = getIntent().getExtras().getString("Hours");
//            if (_bio != null) {
//
//            }
    }


    public void saveButton() {
//        String bio = mBioText.getText().toString().trim();
//
//        Intent i = new Intent();
//        Bundle extra = new Bundle();
//        extra.putString("bio", bio);
//        i.putExtras(extra);
//        setResult(Activity.RESULT_OK, i);
//        finish();
    }

}

