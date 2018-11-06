package com.w8er.android.network;

import com.w8er.android.model.Response;
import com.w8er.android.model.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitInterface {

    //////////////////Auth//////////////////

    @FormUrlEncoded
    @POST("auth/login-signup")
    rx.Observable<Response> phoneLogin(@Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("auth/varify")
    rx.Observable<User> varify(@Field("phone_number") String phone_number, @Field("password") String password);


}
