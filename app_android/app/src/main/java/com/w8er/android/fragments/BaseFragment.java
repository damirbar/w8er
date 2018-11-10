package com.w8er.android.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;


public class BaseFragment extends Fragment {

    FragmentNavigation mFragmentNavigation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            mFragmentNavigation = (FragmentNavigation) context;
        }
    }

    public interface FragmentNavigation {
        void pushFragment(Fragment fragment);
    }
}
