package com.w8er.android.restMenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.w8er.android.R;


public class MenuActivity extends AppCompatActivity {

    private MenuTypesFragment menuTypesFragment;
    private String restId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        if (!getData()) {
            finish();
        }

        if (savedInstanceState == null) {

            loadFragment();
        }
    }



    private void loadFragment(){

        if (menuTypesFragment == null) {

            menuTypesFragment = new MenuTypesFragment();


            Bundle i = new Bundle();
            i.putString("restId", restId);
            menuTypesFragment.setArguments(i);

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame,menuTypesFragment,MenuTypesFragment.TAG).commit();
    }

    private boolean getData() {
        if (getIntent().getExtras() != null) {
            restId = getIntent().getExtras().getString("restId");
            return true;
        } else
            return false;
    }







}
