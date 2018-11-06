package com.w8er.android.entry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.w8er.android.BaseActivity;
import com.w8er.android.R;

import static com.w8er.android.utils.Constants.TOKEN;


public class EntryActivity extends AppCompatActivity  {

    private PhoneLoginFragment mLoginFragment;
    private String mToken;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        initSharedPreferences();
        autoLogin();

        if (savedInstanceState == null) {

            loadFragment();
        }
    }

    private void initSharedPreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(TOKEN,"");
    }

    private void autoLogin() {
        if(!mToken.isEmpty()){
            Intent intent = new Intent(this, BaseActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void loadFragment(){

        if (mLoginFragment == null) {

            mLoginFragment = new PhoneLoginFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame,mLoginFragment,PhoneLoginFragment.TAG).commit();
    }



}
