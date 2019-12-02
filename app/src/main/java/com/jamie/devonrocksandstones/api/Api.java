package com.jamie.devonrocksandstones.api;

import com.jamie.devonrocksandstones.models.DefaultResponse;
import com.jamie.devonrocksandstones.models.LoginResponse;
import com.jamie.devonrocksandstones.models.StoneResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {


    @FormUrlEncoded
    @POST("register")
    Call<DefaultResponse> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("firstName") String firstName,
            @Field("surname") String surname,
            @Field("dateOfBirth") String dateOfBirth,
            @Field("addressLineOne") String addressLineOne,
            @Field("addressLineTwo") String addressLineTwo,
            @Field("city") String city,
            @Field("county") String county,
            @Field("postcode") String postcode
    );

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("stone")
    Call<StoneResponse> getStones(@Header("X-AUTH-TOKEN") String credentials);

    @GET("stone/found/{id}")
    Call<DefaultResponse> foundStone(@Header("X-AUTH-TOKEN") String credentials, @Path("id") int id);
}
