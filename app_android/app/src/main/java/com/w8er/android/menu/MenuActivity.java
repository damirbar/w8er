package com.w8er.android.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageButton;

import com.w8er.android.R;
import com.w8er.android.fragments.RestaurantPageFragment;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class MenuActivity extends AppCompatActivity {

    private MenuTypesFragment menuTypesFragment;
    private String resID;

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
            i.putString("resID", resID);
            menuTypesFragment.setArguments(i);

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame,menuTypesFragment,MenuTypesFragment.TAG).commit();
    }

    private boolean getData() {
        if (getIntent().getExtras() != null) {
            resID = getIntent().getExtras().getString("resID");
            return true;
        } else
            return false;
    }







}
