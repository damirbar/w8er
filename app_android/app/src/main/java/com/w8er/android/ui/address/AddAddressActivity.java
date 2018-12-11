package com.w8er.android.ui.address;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.w8er.android.R;

public class AddAddressActivity extends AppCompatActivity {

    private AddressFragment mAddressFragment;
    private String country;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        getData();

        if (savedInstanceState == null) {

            loadFragment();
        }
    }

    private void getData() {
        if (getIntent().getExtras() != null) {
            address = getIntent().getExtras().getString("address");
            country = getIntent().getExtras().getString("country");
        }
    }


    private void loadFragment(){

        if (mAddressFragment == null) {

            mAddressFragment = new AddressFragment();
            Bundle extra = new Bundle();
            extra.putString("country", country);
            extra.putString("address", address);
            mAddressFragment.setArguments(extra);

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame,mAddressFragment,AddressFragment.TAG).commit();
    }

}
