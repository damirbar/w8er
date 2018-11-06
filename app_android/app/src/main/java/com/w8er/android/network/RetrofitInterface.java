package com.w8er.android.network;

import android.database.Observable;

import com.w8er.android.model.Response;
import com.w8er.android.model.User;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    //////////////////Auth//////////////////

    @POST("auth/new-user")
    Observable<Response> register(@Body User user);



}
