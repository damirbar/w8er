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

public class NumberDialog extends BottomSheetDialogFragment {

    public interface OnCallbackNum {
        void UpdateNum(String num, int id);
    }

    public static final String TAG = NumberDialog.class.getSimpleName();
    private int id;

    private NumberPicker mNumberPicker;
    OnCallbackNum mCallback;

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
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(10);
    }

    private void initViews(View v) {
        mNumberPicker = v.findViewById(R.id.number_picker);
        Button mBtSetCountry = v.findViewById(R.id.button_ok);
        mBtSetCountry.setOnClickListener(view -> onNumSet());
        Button mBtCancel = v.findViewById(R.id.button_cancel);
        mBtCancel.setOnClickListener(view -> dismiss());
    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
            String numStr = bundle.getString("num");
            if (numStr != null) {
                int num = Integer.parseInt(numStr);
                mNumberPicker.setValue(num);
            }
        }
    }

    public void onNumSet() {
        String numStr = String.valueOf(mNumberPicker.getValue());
        mCallback.UpdateNum(numStr, id);
        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnCallbackNum) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}