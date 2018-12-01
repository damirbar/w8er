package com.w8er.android.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.w8er.android.R;
import com.w8er.android.utils.SoftKeyboard;

public class HomeSearchFragment extends BaseFragment {

    private SearchView mSearchViewWords;
    private SearchView mSearchViewLocation;
    private EditText searchEditText;
    private String currentLocation = "Current Location";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home_search, container, false);
        initViews(view);


        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initViews(View v) {
        mSearchViewLocation = v.findViewById(R.id.searchView_loction);

        mSearchViewWords = v.findViewById(R.id.searchView_type);
        new SoftKeyboard(getActivity()).showSoftKeyboard(mSearchViewWords);

        initSearchView();
        Button buttonBack = v.findViewById(R.id.cancel_button);
        buttonBack.setOnClickListener(view -> getActivity().onBackPressed());
        Button searchButton = v.findViewById(R.id.search_button);
        searchButton.setOnClickListener(view -> openSearch());
    }

    private void initSearchView() {
//        Query and Color
        mSearchViewLocation.setQuery(currentLocation, false);
        searchEditText = (EditText) mSearchViewLocation.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.blue));

        //close Btn
        ImageView closeBtn = (ImageView) mSearchViewLocation.findViewById(R.id.search_close_btn);
        Drawable drawableClose = closeBtn.getDrawable();
        closeBtn.setImageDrawable(null);

        mSearchViewLocation.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    searchEditText.setTextColor(getResources().getColor(R.color.black));
                    closeBtn.setImageDrawable(drawableClose);
                    if(currentLocation.equals(mSearchViewLocation.getQuery().toString())){
                        mSearchViewLocation.setQuery("", false);
                    }
                } else {
                    if (mSearchViewLocation.getQuery().length() == 0 || currentLocation.equals(mSearchViewLocation.getQuery().toString())) {
                        mSearchViewLocation.setQuery(currentLocation, false);
                        searchEditText.setTextColor(getResources().getColor(R.color.blue));
                        closeBtn.setImageDrawable(null);
                    }
                }
            }
        });

        mSearchViewLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                openSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            private void callSearch(String query) {
            }
        });

        mSearchViewWords.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                openSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            private void callSearch(String query) {
            }
        });

    }


    private void openSearch() {
        String location = mSearchViewLocation.getQuery().toString().trim();
        String Words = mSearchViewWords.getQuery().toString().trim();

        if(location.isEmpty()){
            location = currentLocation;
            mSearchViewLocation.setQuery(currentLocation, false);
            searchEditText.setTextColor(getResources().getColor(R.color.blue));
        }

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