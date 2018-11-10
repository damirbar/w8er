package com.w8er.android.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.w8er.android.R;
import com.w8er.android.entry.EntryActivity;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;


public class BaseActivity extends AppCompatActivity {

    private NavigationTabBar navigationTabBar;
    private ViewPager pager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        firstTime();
        initViews();
        initNavigationTabBar();
    }

    private void initViews() {
        pager = findViewById(R.id.vp_horizontal_ntb);
        navigationTabBar = findViewById(R.id.ntb_horizontal);
    }

    private void firstTime() {
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun) {

            Intent intent = new Intent(this, EntryActivity.class);
            startActivity(intent);

            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();
        }
    }


    private void initNavigationTabBar() {

        final int color =  getResources().getColor(R.color.red);

        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.home),
                        color).build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.gps),
                        color).build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.settings),
                        color).build()
        );

        pager.setOffscreenPageLimit(models.size()); // How much pages you have
        pager.setCurrentItem(getResources().getInteger(R.integer.start_nav_Bar)); // Returns to First Fragment
        pager.setAdapter(new BasePagerAdapter(getSupportFragmentManager(),models.size()));

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(pager, getResources().getInteger(R.integer.start_nav_Bar));


    }

}
