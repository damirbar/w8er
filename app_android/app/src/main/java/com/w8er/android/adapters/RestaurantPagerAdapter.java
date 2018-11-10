package com.w8er.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.w8er.android.restaurantPage.RestaurantInfoFragment;

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
                return new RestaurantInfoFragment();
            case 1:
                return new RestaurantInfoFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}