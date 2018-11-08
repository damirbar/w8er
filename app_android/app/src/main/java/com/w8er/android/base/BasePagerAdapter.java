package com.w8er.android.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.w8er.android.entry.PhoneVerifyFragment;

public class BasePagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public BasePagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment

                return new PhoneVerifyFragment();
            case 1: // Fragment # 1 - This will show SecondFragment

                return new PhoneVerifyFragment();
            case 2: // Fragment # 2 - This will show ThirdFragment

                return new PhoneVerifyFragment();
            case 3: // Fragment # 3 - This will show FourthFragment

                return new PhoneVerifyFragment();
            case 4: // Fragment # 4 - This will show FifthFragment

                return new PhoneVerifyFragment();
            default:
                return new PhoneVerifyFragment();
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page" + position;
    }

}
