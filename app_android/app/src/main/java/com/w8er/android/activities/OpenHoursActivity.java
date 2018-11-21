package com.w8er.android.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.w8er.android.R;

public class OpenHoursActivity extends AppCompatActivity {

    private Button mBSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_hours);
        initViews();
        getData();
    }

    private void initViews() {
        mBSave = findViewById(R.id.save_button);
        Button mBCancel = findViewById(R.id.cancel_button);
        mBSave.setOnClickListener(view -> saveButton());
        mBCancel.setOnClickListener(view -> finish());
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

