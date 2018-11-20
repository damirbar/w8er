package com.w8er.android.address;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.w8er.android.R;
import com.w8er.android.entry.PhoneLoginFragment;

public class AddAddressActivity extends AppCompatActivity {

    private AddressFragment mAddressFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        if (savedInstanceState == null) {

            loadFragment();
        }
    }

    private void loadFragment(){

        if (mAddressFragment == null) {

            mAddressFragment = new AddressFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame,mAddressFragment,AddressFragment.TAG).commit();
    }

}
