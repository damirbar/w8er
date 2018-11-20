package com.w8er.android.network;

import com.w8er.android.model.Coordinates;
import com.w8er.android.model.Response;
import com.w8er.android.model.User;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;


public interface RetrofitInterface {

    //////////////////Auth//////////////////

    @POST("auth/login-signup")
    Observable<Response> phoneLogin(@Body User user);

    @POST("auth/verify")
    Observable<User> varify(@Body User user);

    //////////////////User//////////////////

    @GET("profile/get-profile")
    Observable<User> getProfile(@Query("phone_number") String phone_number);

    @POST("profile/edit-profile")
    Observable<Response> updateProfile(@Body User user);

    @Multipart
    @POST("profile/post-profile-image")
    Observable<Response> uploadProfileImage(@Part MultipartBody.Part file);

    //////////////////Tools//////////////////

    @GET("tool/address-to-coord")
    Observable<Coordinates> addressToCoord(@Query("address") String address);

}
