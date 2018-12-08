package com.w8er.android.debug.restaurantPage_test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.w8er.android.ui.fragments.ReviewsFragment;

public class RestaurantPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public RestaurantPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ReviewsFragment();
            case 1:
                return new ReviewsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}