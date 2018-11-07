package com.w8er.android.network;

import com.w8er.android.model.Response;
import com.w8er.android.model.User;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;


public interface RetrofitInterface {

    //////////////////Auth//////////////////

    @POST("auth/login-signup")
    Observable<Response> phoneLogin(@Body User user);

    @POST("auth/varify")
    Observable<User> varify(@Body User user);


}
