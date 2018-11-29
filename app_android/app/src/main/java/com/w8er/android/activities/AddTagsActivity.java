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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.volokh.danylo.hashtaghelper.HashTagHelper;
import com.w8er.android.R;
import com.w8er.android.utils.SoftKeyboard;

import java.util.List;

public class AddTagsActivity extends AppCompatActivity {

    private EditText mTagsText;
    private Button mBSave;
    private HashTagHelper mTextHashTagHelper;
    private LinearLayout mAddTag;

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
        mAddTag = findViewById(R.id.layoutTag);
        mTextHashTagHelper = HashTagHelper.Creator.create(getResources().getColor(R.color.HashTag), null);
        mTextHashTagHelper.handle(mTagsText);

        mAddTag.setOnClickListener(view -> addTag());


        mBSave = findViewById(R.id.save_button);
        Button mBCancel = findViewById(R.id.cancel_button);
        mBSave.setOnClickListener(view -> saveButton());
        mBCancel.setOnClickListener(view -> finish());
    }

    private void addTag() {
        String tag = " #";
        mTagsText.append(tag);
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

        String tags = mTagsText.getText().toString().trim().replaceAll("###*","");

        Intent i = new Intent();
        Bundle extra = new Bundle();
        extra.putString("tags", tags);
        i.putExtras(extra);
        setResult(Activity.RESULT_OK, i);
        finish();
    }

}
