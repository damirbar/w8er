package com.w8er.android.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.w8er.android.R;

public class HomeSearchFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home_search, container, false);
        initViews(view);


        return view;
    }

    private void initViews(View v) {

    }






}