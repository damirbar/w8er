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
import android.widget.TextView;

import com.w8er.android.R;
import com.w8er.android.utils.SoftKeyboard;

public class ItemDescriptionActivity extends AppCompatActivity {

    private EditText mDescText;
    private Button mBSave;
    private TextView mTextCount;
    private final int MAX_COUNT = 300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);
        initViews();
        if (!getData()) {
            finish();
        }
    }

    private void initViews() {
        mTextCount = findViewById(R.id.count_num);
        mDescText = findViewById(R.id.desc_text);
        mBSave = findViewById(R.id.save_button);
        Button mBCancel = findViewById(R.id.cancel_button);
        mBSave.setOnClickListener(view -> saveButton());
        mBCancel.setOnClickListener(view -> finish());
        mDescText.addTextChangedListener(mTextEditorWatcher);
    }

    private boolean getData() {
        if (getIntent().getExtras() != null) {
            String description = getIntent().getExtras().getString("description");
            if (description != null) {
                mDescText.setText(description);
                return true;
            } else
                return false;
        } else
            return false;
    }


    public void saveButton() {

        new SoftKeyboard(this).hideSoftKeyboard();

        String desc = mDescText.getText().toString().trim();

        Intent i = new Intent();
        Bundle extra = new Bundle();
        extra.putString("description", desc);
        i.putExtras(extra);
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int num = MAX_COUNT - s.length();
            mTextCount.setText(String.valueOf(num));
            if (num < 0) {
                mBSave.setEnabled(false);
                mBSave.setTextColor(Color.WHITE);
                mTextCount.setTextColor(Color.RED);
            }
            else{
                mBSave.setEnabled(true);
                mTextCount.setTextColor(Color.parseColor("#808080"));
                mBSave.setTextColor(Color.BLACK);
            }
        }
        public void afterTextChanged(Editable s) {
        }
    };


}
