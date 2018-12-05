package com.w8er.android.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.w8er.android.R;

import java.util.Arrays;


public class HourDialog extends BottomSheetDialogFragment {

    public interface OnCallbackHour {
        void UpdateHour(String hour, int id);
    }

    public static final String TAG = HourDialog.class.getSimpleName();
    private int id;

    private NumberPicker mNumberPicker;
    OnCallbackHour mCallback;
    private String[] data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_number_picker_dialog, container, false);
        initViews(view);
        initPicker();
        getData();

        return view;
    }

    private void initPicker() {
        data  = new String[]{"00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30","04:00","04:30"
                ,"05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30","09:00","09:30"
                ,"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30","14:00","14:30"
                ,"15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00","19:30","20:00"
                ,"20:30", "21:00", "21:30","22:00", "22:30", "23:00", "23:30"};
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(data.length-1);
        mNumberPicker.setDisplayedValues(data);

    }

    private void initViews(View v) {
        mNumberPicker = v.findViewById(R.id.number_picker);
        Button mBtSetCountry = v.findViewById(R.id.button_ok);
        mBtSetCountry.setOnClickListener(view -> onHourSet());
        Button mBtCancel = v.findViewById(R.id.button_cancel);
        mBtCancel.setOnClickListener(view -> dismiss());
    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id =  bundle.getInt("id");
            String day = bundle.getString("hour");

            int index = Arrays.asList(data).indexOf(day);
            mNumberPicker.setValue(index);
        }
    }

    public void onHourSet() {
        String hour = data[mNumberPicker.getValue()];
        mCallback.UpdateHour(hour, id);
        dismiss();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnCallbackHour) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}