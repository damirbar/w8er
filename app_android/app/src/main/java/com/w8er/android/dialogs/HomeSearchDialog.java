package com.w8er.android.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.w8er.android.R;
import com.w8er.android.model.SearchRest;
import com.w8er.android.utils.SoftKeyboard;

public class HomeSearchDialog extends DialogFragment {

    public interface OnCallbackSearch {
        void UpdateSearch(SearchRest query);
    }

    OnCallbackSearch mCallback;
    private SearchView mSearchViewWords;
    private SearchView mSearchViewLocation;
    private EditText searchEditText;
    private ImageButton imageButtonSearch;
    private String currentLocation = "Current Location";
    public static final String TAG = HomeSearchDialog.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        mCallback = (OnCallbackSearch) getTargetFragment();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_home_search, container, false);
        initViews(view);


        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initViews(View v) {
        mSearchViewLocation = v.findViewById(R.id.searchView_loction);
        imageButtonSearch = v.findViewById(R.id.imageButtonSearch);
        mSearchViewWords = v.findViewById(R.id.searchView_type);
        new SoftKeyboard(getActivity()).showSoftKeyboard(mSearchViewWords);

        initSearchView();
        imageButtonSearch.setOnClickListener(view -> openSearch());
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
                    if (currentLocation.equals(mSearchViewLocation.getQuery().toString())) {
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

        String[] words = mSearchViewWords.getQuery().toString().trim().split(" ");


        if (location.isEmpty()) {
            location = currentLocation;
        }

        SearchRest searchRest = new SearchRest();
        searchRest.setAddress(location);
        searchRest.setTags(words);

        mCallback.UpdateSearch(searchRest);
        dismiss();

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
            getDialog().getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(android.content.DialogInterface dialog,
                                 int keyCode, android.view.KeyEvent event) {
                if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
                    // To dismiss the fragment when the back-button is pressed.
                    dismiss();
                    return true;
                }
                // Otherwise, do nothing else
                else return false;
            }
        });
    }
}