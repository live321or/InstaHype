package com.likesmm.instahype.welcome;

import com.likesmm.instahype.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("reg.php")
    Call<User> performRegistration(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<User> performUserLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("getBalance.php")
    Call<User> getBalance(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("setOrder.php")
    Call<User> setOrder(
            @Field("email") String email,
            @Field("count") double count,
            @Field("price") double price,
            @Field("type") String type,
            @Field("link") String link
    );

    @FormUrlEncoded
    @POST("getOrders.php")
    Call<List<Order>> getOrders(
            @Field("email") String email
    );
}
