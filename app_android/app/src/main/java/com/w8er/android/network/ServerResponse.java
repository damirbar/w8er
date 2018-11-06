package com.w8er.android.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.w8er.android.model.Response;

import retrofit2.adapter.rxjava.HttpException;


public class ServerResponse {

    private Context context;

    public ServerResponse(Context context) {
        this.context = context;
    }


    private void toastMessage(String message) {

        Toast.makeText(context , message, Toast.LENGTH_SHORT).show();

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
