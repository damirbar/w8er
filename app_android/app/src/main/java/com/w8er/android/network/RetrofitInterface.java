package com.w8er.android.network;

import com.w8er.android.model.Coordinates;
import com.w8er.android.model.Response;
import com.w8er.android.model.Restaurant;
import com.w8er.android.model.Searchable;
import com.w8er.android.model.User;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;


public interface RetrofitInterface {

    //////////////////search//////////////////

    @GET("search/free-text-search")
    Observable<Searchable> getFreeTextSearch(@Query("keyword") String keyword);


    //////////////////Auth//////////////////

    @POST("auth/login-signup")
    Observable<Response> phoneLogin(@Body User user);

    @POST("auth/verify")
    Observable<User> varify(@Body User user);

    //////////////////User//////////////////

    @GET("user/get-profile")
    Observable<User> getProfile(@Query("phone_number") String phone_number);

    @POST("user/edit-profile")
    Observable<Response> updateProfile(@Body User user);

    @Multipart
    @POST("user/post-profile-image")
    Observable<Response> uploadProfileImage(@Part MultipartBody.Part file);

    //////////////////Restaurant//////////////////

    @POST("rest/create")
    Observable<Restaurant> createRestaurant(@Body Restaurant restaurant);

    @GET("rest/get-rest")
    Observable<Restaurant> getRest(@Query("id") String id);

    @POST("rest/edit-rest")
    Observable<Response> updateRestaurant(@Body Restaurant restaurant);

    //////////////////Tools//////////////////

    @GET("tool/address-to-coord")
    Observable<Coordinates> addressToCoord(@Query("address") String address);

}
