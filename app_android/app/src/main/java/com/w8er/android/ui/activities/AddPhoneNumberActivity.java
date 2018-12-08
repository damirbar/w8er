package com.w8er.android.ui.activities;

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
import com.w8er.android.utils.SoftKeyboard;

import static com.w8er.android.utils.DataFormatter.getCountryCode;

public class AddPhoneNumberActivity extends AppCompatActivity {

    private EditText mPhoneNumber;
    private Button mBSave;
    private CountryCodePicker ccp;

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
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(mPhoneNumber);
        mBSave = findViewById(R.id.save_button);
        Button mBCancel = findViewById(R.id.cancel_button);
        mBSave.setOnClickListener(view -> saveButton());
        mBCancel.setOnClickListener(view -> finish());
        mPhoneNumber.addTextChangedListener(mTextEditorWatcher);

    }

    private boolean getData() {
        if (getIntent().getExtras() != null) {
            String _phone = getIntent().getExtras().getString("phone");
            String _country = getIntent().getExtras().getString("country");
            if (_phone != null && _country != null) {
                String countryCode = getCountryCode(_country);
                ccp.setCountryForNameCode(countryCode);
                if (!_phone.isEmpty()) {
                    String codeNum = ccp.getSelectedCountryCode() + " ";
                    _phone = _phone.replaceFirst(codeNum, "");
                    _phone = _phone.substring(1);
                }
                mPhoneNumber.setText(_phone);
                return true;
            } else
                return false;
        } else
            return false;
    }

    public void saveButton() {

        new SoftKeyboard(this).hideSoftKeyboard();

        String phone = ccp.getFormattedFullNumber();
        Intent i = new Intent();
        Bundle extra = new Bundle();
        extra.putString("phone", phone);
        i.putExtras(extra);
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (ccp.isValidFullNumber() || mPhoneNumber.getText().toString().isEmpty()) {
                mBSave.setEnabled(true);
                mBSave.setTextColor(Color.BLACK);
            } else {
                mBSave.setEnabled(false);
                mBSave.setTextColor(Color.WHITE);

            }
        }

        public void afterTextChanged(Editable s) {
        }
    };


}
