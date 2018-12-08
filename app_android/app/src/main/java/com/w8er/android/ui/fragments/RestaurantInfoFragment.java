package com.w8er.android.ui.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.w8er.android.R;
import com.w8er.android.model.TimeSlot;

import java.util.ArrayList;
import java.util.Calendar;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class RestaurantInfoFragment extends Fragment {

    private TextView mHours1;
    private TextView mHours2;
    private TextView mHours3;
    private TextView mHours4;
    private TextView mHours5;
    private TextView mHours6;
    private TextView mHours7;

    private TextView mDay1;
    private TextView mDay2;
    private TextView mDay3;
    private TextView mDay4;
    private TextView mDay5;
    private TextView mDay6;
    private TextView mDay7;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_restaurant_info, container, false);
        initViews(view);

        getData();

        return view;
    }

    private void initViews(View v) {
        mHours1 = v.findViewById(R.id.hours_1);
        mHours2 = v.findViewById(R.id.hours_2);
        mHours3 = v.findViewById(R.id.hours_3);
        mHours4 = v.findViewById(R.id.hours_4);
        mHours5 = v.findViewById(R.id.hours_5);
        mHours6 = v.findViewById(R.id.hours_6);
        mHours7 = v.findViewById(R.id.hours_7);
        mDay1 = v.findViewById(R.id.day_1);
        mDay2 = v.findViewById(R.id.day_2);
        mDay3 = v.findViewById(R.id.day_3);
        mDay4 = v.findViewById(R.id.day_4);
        mDay5 = v.findViewById(R.id.day_5);
        mDay6 = v.findViewById(R.id.day_6);
        mDay7 = v.findViewById(R.id.day_7);
        ImageButton buttonBack = v.findViewById(R.id.image_Button_back);
        buttonBack.setOnClickListener(view -> getActivity().onBackPressed());
        ScrollView scrollView = v.findViewById(R.id.scroll_view);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);

    }

    private void getData() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ArrayList<TimeSlot> hours = bundle.getParcelableArrayList("hours");
            initHours(hours);
        }
    }

    private void initHours(ArrayList<TimeSlot> hours) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                mDay1.setTypeface(mDay1.getTypeface(), Typeface.BOLD);
                mHours1.setTypeface(mHours1.getTypeface(), Typeface.BOLD);
                break;
            case Calendar.MONDAY:
                mDay2.setTypeface(mDay2.getTypeface(), Typeface.BOLD);
                mHours2.setTypeface(mHours2.getTypeface(), Typeface.BOLD);
                break;
            case Calendar.TUESDAY:
                mDay3.setTypeface(mDay3.getTypeface(), Typeface.BOLD);
                mHours3.setTypeface(mHours3.getTypeface(), Typeface.BOLD);
                break;
            case Calendar.WEDNESDAY:
                mDay4.setTypeface(mDay4.getTypeface(), Typeface.BOLD);
                mHours4.setTypeface(mHours4.getTypeface(), Typeface.BOLD);
                break;
            case Calendar.THURSDAY:
                mDay5.setTypeface(mDay5.getTypeface(), Typeface.BOLD);
                mHours5.setTypeface(mHours5.getTypeface(), Typeface.BOLD);
                break;
            case Calendar.FRIDAY:
                mDay6.setTypeface(mDay6.getTypeface(), Typeface.BOLD);
                mHours6.setTypeface(mHours6.getTypeface(), Typeface.BOLD);
                break;
            case Calendar.SATURDAY:
                mDay7.setTypeface(mDay7.getTypeface(), Typeface.BOLD);
                mHours7.setTypeface(mHours7.getTypeface(), Typeface.BOLD);
                break;
        }

        String closed = "Closed";
        mHours1.setText(closed);
        mHours2.setText(closed);
        mHours3.setText(closed);
        mHours4.setText(closed);
        mHours5.setText(closed);
        mHours6.setText(closed);
        mHours7.setText(closed);

        for (TimeSlot t : hours) {
            if (t.getDays().equalsIgnoreCase("Sun")) {
                String timeSlots = mHours1.getText().toString().trim();
                if (timeSlots.equalsIgnoreCase(closed)) {
                    timeSlots = t.toStringHours();
                } else
                    timeSlots += "\n" + t.toStringHours();
                mHours1.setText(timeSlots);
            }
            if (t.getDays().equalsIgnoreCase("Mon")) {
                String timeSlots = mHours2.getText().toString().trim();
                if (timeSlots.equalsIgnoreCase(closed)) {
                    timeSlots = t.toStringHours();
                } else
                    timeSlots += "\n" + t.toStringHours();
                mHours2.setText(timeSlots);
            }
            if (t.getDays().equalsIgnoreCase("Tue")) {
                String timeSlots = mHours3.getText().toString().trim();
                if (timeSlots.equalsIgnoreCase(closed)) {
                    timeSlots = t.toStringHours();
                } else
                    timeSlots += "\n" + t.toStringHours();
                mHours3.setText(timeSlots);
            }
            if (t.getDays().equalsIgnoreCase("Wed")) {
                String timeSlots = mHours4.getText().toString().trim();
                if (timeSlots.equalsIgnoreCase(closed)) {
                    timeSlots = t.toStringHours();
                } else
                    timeSlots += "\n" + t.toStringHours();
                mHours4.setText(timeSlots);
            }
            if (t.getDays().equalsIgnoreCase("Thu")) {
                String timeSlots = mHours5.getText().toString().trim();
                if (timeSlots.equalsIgnoreCase(closed)) {
                    timeSlots = t.toStringHours();
                } else
                    timeSlots += "\n" + t.toStringHours();
                mHours5.setText(timeSlots);
            }
            if (t.getDays().equalsIgnoreCase("Fri")) {
                String timeSlots = mHours6.getText().toString().trim();
                if (timeSlots.equalsIgnoreCase(closed)) {
                    timeSlots = t.toStringHours();
                } else
                    timeSlots += "\n" + t.toStringHours();
                mHours6.setText(timeSlots);
            }
            if (t.getDays().equalsIgnoreCase("Sat")) {
                String timeSlots = mHours7.getText().toString().trim();
                if (timeSlots.equalsIgnoreCase(closed)) {
                    timeSlots = t.toStringHours();
                } else
                    timeSlots += "\n" + t.toStringHours();
                mHours7.setText(timeSlots);
            }
        }
    }
}