package com.w8er.android.entry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.w8er.android.R;


public class EntryActivity extends AppCompatActivity  {

    private PhoneLoginFragment mLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        if (savedInstanceState == null) {

            loadFragment();
        }
    }


    private void loadFragment(){

        if (mLoginFragment == null) {

            mLoginFragment = new PhoneLoginFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame,mLoginFragment,PhoneLoginFragment.TAG).commit();
    }



}
