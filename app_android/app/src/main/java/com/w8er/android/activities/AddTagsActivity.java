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

import com.volokh.danylo.hashtaghelper.HashTagHelper;
import com.w8er.android.R;
import com.w8er.android.utils.SoftKeyboard;

import java.util.List;

public class AddTagsActivity extends AppCompatActivity {

    private EditText mTagsText;
    private Button mBSave;
    private HashTagHelper mTextHashTagHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tags);
        initViews();
        if (!getData()) {
            finish();
        }
    }

    private void initViews() {
        mTagsText = findViewById(R.id.tags_text);
        mTextHashTagHelper = HashTagHelper.Creator.create(getResources().getColor(R.color.HashTag), null);
        mTextHashTagHelper.handle(mTagsText);


        mBSave = findViewById(R.id.save_button);
        Button mBCancel = findViewById(R.id.cancel_button);
        mBSave.setOnClickListener(view -> saveButton());
        mBCancel.setOnClickListener(view -> finish());
        mTagsText.addTextChangedListener(mTextEditorWatcher);

    }

    private boolean getData() {
        if (getIntent().getExtras() != null) {
            String _tags = getIntent().getExtras().getString("tags");
            if (_tags != null) {
                mTagsText.setText(_tags);
                return true;
            } else
                return false;
        } else
            return false;
    }

    public void saveButton() {

        new SoftKeyboard(this).hideSoftKeyboard();

        String tags = mTagsText.getText().toString().trim();

        Intent i = new Intent();
        Bundle extra = new Bundle();
        extra.putString("tags", tags);
        i.putExtras(extra);
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            List<String> allHashTags = mTextHashTagHelper.getAllHashTags();

            if (allHashTags.size() >= 2 || allHashTags.size() == 0) {
                mBSave.setEnabled(true);
                mBSave.setTextColor(Color.BLACK);
            }
            else{
                mBSave.setEnabled(false);
                mBSave.setTextColor(Color.WHITE);

            }
        }
        public void afterTextChanged(Editable s) {
        }
    };


}
