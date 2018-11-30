package com.w8er.android.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.w8er.android.R;
import com.w8er.android.utils.SoftKeyboard;

import java.lang.reflect.Field;

public class HomeSearchFragment extends BaseFragment {

    private SearchView  mSearchViewWords;
    private SearchView mSearchViewLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home_search, container, false);
        initViews(view);


        return view;
    }

    private void initViews(View v) {
        mSearchViewWords = v.findViewById(R.id.searchView_type);
        mSearchViewLocation = v.findViewById(R.id.searchView_loction);
        initSearchView();
        Button buttonBack = v.findViewById(R.id.cancel_button);
        buttonBack.setOnClickListener(view -> getActivity().onBackPressed());
        Button searchButton = v.findViewById(R.id.search_button);
        searchButton.setOnClickListener(view -> openSearch());



    }

    private void initSearchView(){


    }


    private void openSearch() {
        String location = mSearchViewLocation.getQuery().toString().trim();
        String Words = mSearchViewWords.getQuery().toString().trim();

        String query = Words + " " + location;


        new SoftKeyboard(getActivity()).hideSoftKeyboard();

        Bundle i = new Bundle();
        i.putString("query", query);
        SearchResultsFragment frag = new SearchResultsFragment();
        frag.setArguments(i);

        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(frag);
        }


    }


}