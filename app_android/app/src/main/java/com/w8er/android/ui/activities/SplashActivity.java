package com.w8er.android.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.w8er.android.debug.SetUpIpActivity;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Need to be removed!
        Intent intent = new Intent(this, EditRestLayoutActivity.class);

//        Intent intent = new Intent(this, SetUpIpActivity.class);

//        Intent intent = new Intent(this, NavBarActivity.class);
        startActivity(intent);
        finish();
    }
}
