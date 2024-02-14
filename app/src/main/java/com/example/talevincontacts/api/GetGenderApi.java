package com.example.talevincontacts.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetGenderApi {
    @GET("/")
    Call<Retrofit.GenderInput> getGender(@Query("name") String name);
}
