package com.w8er.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ScrollView;

import com.w8er.android.R;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;


public class AddRestaurantActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_UPDATE_NOTES = 0x1;
    public static final int REQUEST_CODE_UPDATE_PHONE_NUMBER = 0x2;

    private Button countryBtn;
    private NumberPicker mNumberPicker;
    private String countryNames[];
    private EditText eTNotes;
    private EditText eTPhone;
    private EditText eTname;
    private EditText eTaddress;
    private EditText eTwebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        initViews();
        initCountriesPicker();

    }

    private void initCountriesPicker() {
        ScrollView scrollView = findViewById(R.id.scroll_view);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        countryNames = new String[]{"United States", "Israel"};
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(countryNames.length - 1);
        mNumberPicker.setDisplayedValues(countryNames);
    }

    private void initViews() {
        countryBtn = findViewById(R.id.country_button);
        mNumberPicker = findViewById(R.id.number_picker);
        mNumberPicker.setOnScrollListener((view, SCROLL_STATE_IDLE) -> changeCountry());
        countryBtn.setOnClickListener(view -> OpenCloseList());
        eTNotes = findViewById(R.id.eTNotes);
        eTNotes.setOnClickListener(view -> setNotes());
        eTPhone = findViewById(R.id.eTphoneNumbe);
        eTPhone.setOnClickListener(view -> setPhone());
        eTname = findViewById(R.id.eName);
        eTaddress = findViewById(R.id.eTaddress);
        eTwebsite = findViewById(R.id.eTWebsite);
        Button mBCancel = findViewById(R.id.cancel_button);
        mBCancel.setOnClickListener(view -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {

        if (requestCode == REQUEST_CODE_UPDATE_NOTES) {
            if (resultCode == RESULT_OK) {
                Bundle extra = result.getExtras();
                String notes = extra.getString("notes");
                eTNotes.setText(notes);
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
        if (requestCode == REQUEST_CODE_UPDATE_PHONE_NUMBER) {
            if (resultCode == RESULT_OK) {
                Bundle extra = result.getExtras();
                String phone = extra.getString("phone");
                String countryCode = extra.getString("countryCode");
                if(phone!=null)
                    eTPhone.setText(phone.replace(countryCode, ""));
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }

    private void setNotes() {
        Intent i = new Intent(this, NotesActivity.class);
        String notes = eTNotes.getText().toString().trim();
        Bundle extra = new Bundle();
        extra.putString("notes", notes);
        i.putExtras(extra);
        startActivityForResult(i, REQUEST_CODE_UPDATE_NOTES);
    }

    private void setPhone() {
        Intent i = new Intent(this, AddPhoneNumberActivity.class);
        String phone = eTPhone.getText().toString().trim();
        String country = countryBtn.getText().toString().trim();
        Bundle extra = new Bundle();
        extra.putString("phone", phone);
        extra.putString("country", country);
        i.putExtras(extra);
        startActivityForResult(i, REQUEST_CODE_UPDATE_PHONE_NUMBER);
    }


    private void changeCountry() {
        String country = countryNames[mNumberPicker.getValue()];
        countryBtn.setText(country);
        eTPhone.setText("");
    }

    private void OpenCloseList() {
        if (mNumberPicker.getVisibility() == View.VISIBLE) {
            mNumberPicker.setVisibility(View.GONE);

        } else
            mNumberPicker.setVisibility(View.VISIBLE);
    }


}
