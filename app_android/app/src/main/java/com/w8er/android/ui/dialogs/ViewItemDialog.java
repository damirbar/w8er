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
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.w8er.android.R;

import java.util.Arrays;


public class ViewItemDialog extends BottomSheetDialogFragment {

    public interface OnCallbackItem {
        void UpdateItem(String item);
    }

    public static final String TAG = ViewItemDialog.class.getSimpleName();

    private NumberPicker mNumberPicker;
    OnCallbackItem mCallback;
    private String[] data;
    private RelativeLayout tableLayout;
    private int amount = 1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_picker_dialog, container, false);
        initViews(view);
        initPicker();
        getData();

        return view;
    }

    private void initPicker() {
        data = new String[]{"Table", "Exit", "Restroom","Bar","Remove"};
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(data.length-1);
        mNumberPicker.setDisplayedValues(data);

    }

    private void initViews(View v) {
        tableLayout = v.findViewById(R.id.table_layout);
        mNumberPicker = v.findViewById(R.id.number_picker);
        Button mBtSetItem = v.findViewById(R.id.button_ok);
        mBtSetItem.setOnClickListener(view -> onItemSet());
        Button mBtCancel = v.findViewById(R.id.button_cancel);
        mBtCancel.setOnClickListener(view -> dismiss());
        mNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> valueChange());

        TextView mAmount = v.findViewById(R.id.size);
        ImageButton mAdd = v.findViewById(R.id.image_add);
        ImageButton mRemove = v.findViewById(R.id.image_remove);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    amount++;
                    if (amount > 20) amount = 20;
                    String num = String.valueOf(amount);
                    mAmount.setText(num);

            }
        });

        mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    amount--;
                    if (amount < 1) amount = 1;
                    String num = String.valueOf(amount);
                    mAmount.setText(num);

            }
        });

    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String item = bundle.getString("item");

            int index = Arrays.asList(data).indexOf(item);
            mNumberPicker.setValue(index);
        }
    }

    public void onItemSet() {
        String item = data[mNumberPicker.getValue()];
        mCallback.UpdateItem(item);
        dismiss();
    }

    public void valueChange() {
        String item = data[mNumberPicker.getValue()];

        if(item.equals("Table"))
            tableLayout.setVisibility(View.VISIBLE);
        else
            tableLayout.setVisibility(View.GONE);


    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnCallbackItem) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}