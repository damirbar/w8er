package com.w8er.android.network;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.w8er.android.R;
import com.w8er.android.model.Response;

import retrofit2.adapter.rxjava.HttpException;

public class ServerResponse {

    private View layout;

    public ServerResponse(View _layout) {
        this.layout = _layout;
    }

    public View getLayout() {
        return layout;
    }

    public void setLayout(View layout) {
        this.layout = layout;
    }

    public void showSnackBarMessage(String message) {

        if(message !=null && !message.isEmpty()) {

            String capitalize = message.substring(0, 1).toUpperCase() + message.substring(1);

            try {
                final Snackbar snackbar = Snackbar.make(layout, capitalize, Snackbar.LENGTH_SHORT);
                snackbar.getView().setBackgroundColor(Color.parseColor("#df5a55"));
                ((TextView) snackbar.getView().findViewById(R.id.snackbar_text))
                        .setTextColor(Color.parseColor("#ffffff"));
                snackbar.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handleError(Throwable error) {
        Log.d("error", error.toString());
        try {
            if (error instanceof HttpException) {
                Gson gson = new GsonBuilder().setLenient().create();
                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody, Response.class);
                showSnackBarMessage(response.getMessage());
            } else {
                showSnackBarMessage("No Internet Connection.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showSnackBarMessage("Internal Server Error.");
        }
    }

}
