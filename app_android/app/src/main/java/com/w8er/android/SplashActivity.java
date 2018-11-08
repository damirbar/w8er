package com.w8er.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.w8er.android.base.BaseActivity;
import com.w8er.android.entry.EntryActivity;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, BaseActivity.class);

        startActivity(intent);
        finish();
    }
}
