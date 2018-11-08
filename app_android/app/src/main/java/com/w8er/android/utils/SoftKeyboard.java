package com.w8er.android.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SoftKeyboard {

    private Activity activity;

    public SoftKeyboard(Activity activity) {
        this.activity = activity;
    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if(activity.getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

}
