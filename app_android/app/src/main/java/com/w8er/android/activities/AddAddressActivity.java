package com.w8er.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;
import com.w8er.android.R;

public class AddAddressActivity extends AppCompatActivity {

    private EditText mPhoneNumber;
    private Button mBSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_number);
        initViews();
        if (!getData()) {
            finish();
        }
    }

    private void initViews() {
        mPhoneNumber = findViewById(R.id.editText_carrierNumber);
        mBSave = findViewById(R.id.save_button);
        Button mBCancel = findViewById(R.id.cancel_button);
        mBSave.setOnClickListener(view -> saveButton());
        mBCancel.setOnClickListener(view -> finish());

    }

    private boolean getData() {
        if (getIntent().getExtras() != null) {
            String _phone = getIntent().getExtras().getString("phone");
            if (_phone != null) {
                mPhoneNumber.setText(_phone);
                return true;
            } else
                return false;
        } else
            return false;
    }

    public void saveButton() {
        String phone = "";
        Intent i = new Intent();
        Bundle extra = new Bundle();
        extra.putString("phone", phone);
        i.putExtras(extra);
        setResult(Activity.RESULT_OK, i);
        finish();
    }


}
