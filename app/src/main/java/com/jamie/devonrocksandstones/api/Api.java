package com.jamie.devonrocksandstones.api;

import com.jamie.devonrocksandstones.models.ClueResponse;
import com.jamie.devonrocksandstones.models.DefaultResponse;
import com.jamie.devonrocksandstones.models.LoginResponse;
import com.jamie.devonrocksandstones.models.PostResponse;
import com.jamie.devonrocksandstones.models.StoneResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("post")
    Call<PostResponse> getPosts(@Header("X-AUTH-TOKEN") String credentials);

    @GET("stone")
    Call<StoneResponse> getStones(@Header("X-AUTH-TOKEN") String credentials);

    @GET("stone/{stone}/clues/{location}")
    Call<ClueResponse> getClues(@Header("X-AUTH-TOKEN") String credentials, @Path("stone") int id, @Path("location") int location);

    @GET("stone/{stone}/found/{location}")
    Call<DefaultResponse> foundStone(@Header("X-AUTH-TOKEN") String credentials, @Path("stone") int id, @Path("location") int location);

    @FormUrlEncoded
    @POST("stone/add")
    Call<DefaultResponse> addStone(@Header("X-AUTH-TOKEN") String credentials, @Field("location") String location);

    @Multipart
    @POST("post/add")
    Call<DefaultResponse> addPost(@Header("X-AUTH-TOKEN") String credentials, @Part MultipartBody.Part file, @Part("name") RequestBody requestBody, @Part("content") String content);

    @DELETE("post/delete/{id}")
    Call<DefaultResponse> deletePost(@Header("X-AUTH-TOKEN") String credentials, @Path("id") int id);


    @DELETE("stone/clue/delete/{id}")
    Call<DefaultResponse> deleteClue(@Header("X-AUTH-TOKEN") String credentials, @Path("id") int id);

    @Multipart
    @POST("stone/{stone}/add-clue/{location}")
    Call<DefaultResponse> addClue(@Header("X-AUTH-TOKEN") String credentials, @Path("stone") int id, @Path("location") int location, @Part MultipartBody.Part file, @Part("name") RequestBody requestBody, @Part("content") String content);
}
