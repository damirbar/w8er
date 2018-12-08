package com.w8er.android.ui.dialogs;

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


public class GenderDialog extends BottomSheetDialogFragment {

    public interface OnCallbackGender {
        void UpdateGender(String gender);
    }

    public static final String TAG = GenderDialog.class.getSimpleName();

    private NumberPicker mNumberPicker;
    GenderDialog.OnCallbackGender mCallback;
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
        data = new String[]{"Male", "Female", "Not Specified"};
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(data.length-1);
        mNumberPicker.setDisplayedValues(data);

    }

    private void initViews(View v) {
        mNumberPicker = v.findViewById(R.id.number_picker);
        Button mBtSetGender = v.findViewById(R.id.button_ok);
        mBtSetGender.setOnClickListener(view -> onGenderSet());
        Button mBtCancel = v.findViewById(R.id.button_cancel);
        mBtCancel.setOnClickListener(view -> dismiss());
    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String day = bundle.getString("gender");

            int index = Arrays.asList(data).indexOf(day);
            mNumberPicker.setValue(index);
        }
    }

    public void onGenderSet() {
        String gender = data[mNumberPicker.getValue()];
        mCallback.UpdateGender(gender);
        dismiss();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (GenderDialog.OnCallbackGender) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}