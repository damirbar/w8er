package com.w8er.android.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavSwitchController;
import com.ncapdevi.fragnav.FragNavTransactionOptions;
import com.ncapdevi.fragnav.tabhistory.FragNavTabHistoryController;
import com.w8er.android.adapters.BasePagerAdapter;
import com.w8er.android.fragments.MapsFragment;
import com.w8er.android.R;
import com.w8er.android.entry.EntryActivity;
import com.w8er.android.fragments.BaseFragment;
import com.w8er.android.fragments.HomeFragment;
import com.w8er.android.fragments.SettingsFragment;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;


public class NavBarActivity extends AppCompatActivity implements BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {

    private NavigationTabBar navigationTabBar;
    private FragNavController mNavController;
    private ViewPager pager;

    private final int INDEX_HOME = FragNavController.TAB1;
    private final int INDEX_MAP = FragNavController.TAB2;
    private final int INDEX_SETTINGS = FragNavController.TAB3;

    private int oldTabSelectIndex;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);
        initViews();
        initNavigationTabBar(savedInstanceState);
        firstTime();
    }

    private void initViews() {
        navigationTabBar = findViewById(R.id.ntb_horizontal);
        pager = findViewById(R.id.container);
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

    private void initNavigationTabBar(Bundle savedInstanceState) {

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

        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.container)
                .transactionListener(this)
                .rootFragmentListener(this, models.size())
                .popStrategy(FragNavTabHistoryController.UNIQUE_TAB_HISTORY)
                .switchController(new FragNavSwitchController() {
                    @Override
                    public void switchTab(int index, FragNavTransactionOptions transactionOptions) {
                        navigationTabBar.setModelIndex(index);

                    }
                })
                .build();


        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {

                if(oldTabSelectIndex == index){
                    mNavController.clearStack();
                    return;
                }
                switch (index) {
                    case 0:
                        mNavController.switchTab(INDEX_HOME);
                        break;
                    case 1:
                        mNavController.switchTab(INDEX_MAP);
                        break;
                    case 2:
                        mNavController.switchTab(INDEX_SETTINGS);
                        break;
                }
                oldTabSelectIndex = index;
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                model.hideBadge();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (!mNavController.popFragment()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNavController != null) {
            mNavController.onSaveInstanceState(outState);
        }
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }


    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case INDEX_HOME:
                return new HomeFragment();
            case INDEX_MAP:
                return new MapsFragment();
            case INDEX_SETTINGS:
                return new SettingsFragment();
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mNavController.popFragment();
                break;
        }
        return true;
    }

}
