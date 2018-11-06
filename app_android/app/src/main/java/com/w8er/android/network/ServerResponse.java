package com.w8er.android.network;

import android.app.Activity;
import android.util.Log;

import com.devspark.appmsg.AppMsg;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.w8er.android.model.Response;

import retrofit2.adapter.rxjava.HttpException;


public class ServerResponse {

    private Activity activity;

    public ServerResponse(Activity activity) {
        this.activity = activity;
    }


    private void toastMessage(String message) {

        AppMsg.makeText(activity, message, AppMsg.STYLE_ALERT).show();
    }

    public void handleError(Throwable error) {
        Log.d("error", error.toString());
        try {
            if (error instanceof HttpException) {
                Gson gson = new GsonBuilder().setLenient().create();
                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody, Response.class);
                toastMessage(response.getMessage());
            } else {
                toastMessage("No Internet Connection.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            toastMessage("Internal Server Error.");
        }
    }


}
