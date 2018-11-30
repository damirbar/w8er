package com.w8er.android.network;

import com.w8er.android.model.Coordinates;
import com.w8er.android.model.RestItem;
import com.w8er.android.model.MenuRest;
import com.w8er.android.model.Response;
import com.w8er.android.model.Restaurant;
import com.w8er.android.model.Restaurants;
import com.w8er.android.model.Review;
import com.w8er.android.model.Searchable;
import com.w8er.android.model.User;

import java.util.List;

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

    @POST("user/review")
    Observable<Response> postReview(@Body Review review);

    @GET("user/get-favorites")
    Observable<Restaurants> getFavoritesRest();

    @GET("user/get-my-restaurants")
    Observable<Restaurants> getMyRest();


    @POST("user/add-to-favorites")
    Observable<Response> addToFavorites(@Body Restaurant restaurant);

    @POST("user/remove-from-favorites")
    Observable<Response> removeFromFavorites(@Body Restaurant restaurant);

    //////////////////Restaurant//////////////////

    @POST("rest/create")
    Observable<Restaurant> createRestaurant(@Body Restaurant restaurant);

    @GET("rest/get-rest")
    Observable<Restaurant> getRest(@Query("restId") String restId);

    @GET("rest/get-menu")
    Observable<List<RestItem>> getMenu(@Query("restId") String restId, @Query("type") String type);

    @GET("rest/get-item-by-id")
    Observable<RestItem> getMenuItem(@Query("id") String id);

    //////////////////Restaurant - Auth//////////////////

    @POST("restAuth/edit-rest")
    Observable<Response> updateRestaurant(@Body Restaurant restaurant);

    @Multipart
    @POST("restAuth/post-profile-image")
    Observable<Response> uploadProfileImageRes(@Query("restId") String restId , @Part MultipartBody.Part file);

    @Multipart
    @POST("restAuth/add-pic")
    Observable<Response> uploadImage(@Query("restId") String restId , @Part MultipartBody.Part file);

    @POST("restAuth/add-item")
    Observable<Response> postMenuItem(@Query("restId") String restId , @Body RestItem restItem);

    @POST("restAuth/remove-item")
    Observable<Response> removeItem(@Query("restId") String restId, @Body RestItem RestItem);

    @POST("restAuth/edit-item")
    Observable<Response> editMenuItem(@Query("restId") String restId, @Body RestItem restItem);

    @Multipart
    @POST("restAuth/edit-item-photo")
    Observable<Response> editItemImage(@Query("restId") String restId, @Query("id") String id, @Part MultipartBody.Part file);

    //////////////////Tools//////////////////

    @GET("tool/address-to-coord")
    Observable<Coordinates> addressToCoord(@Query("address") String address);

    @GET("tool/find-near-location")
    Observable<Restaurants> findNearLocation(@Query("dist") double dist, @Query("lat") double lat, @Query("lng") double lng);


}
