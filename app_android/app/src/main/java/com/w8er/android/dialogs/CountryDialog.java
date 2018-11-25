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


public class CountryDialog extends BottomSheetDialogFragment {

    public interface OnCallbackCountry {
        void UpdateCountry(String country);
    }

    public static final String TAG = CountryDialog.class.getSimpleName();

    private NumberPicker mNumberPicker;
    OnCallbackCountry mCallback;
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
        data = new String[]{"Israel", "United States"};
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(data.length-1);
        mNumberPicker.setDisplayedValues(data);

    }

    private void initViews(View v) {
        mNumberPicker = v.findViewById(R.id.number_picker);
        Button mBtSetCountry = v.findViewById(R.id.button_ok);
        mBtSetCountry.setOnClickListener(view -> onCountrySet());
        Button mBtCancel = v.findViewById(R.id.button_cancel);
        mBtCancel.setOnClickListener(view -> dismiss());
    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String country = bundle.getString("country");
            if(country!=null) {
                int i = 0;
                if (country.equalsIgnoreCase("United States"))
                    i = 1;
                mNumberPicker.setValue(i);
            }
        }
    }

    public void onCountrySet() {
        String country = data[mNumberPicker.getValue()];
        mCallback.UpdateCountry(country);
        dismiss();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnCallbackCountry) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}