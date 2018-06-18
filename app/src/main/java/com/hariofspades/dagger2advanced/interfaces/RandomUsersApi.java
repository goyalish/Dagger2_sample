package com.hariofspades.dagger2advanced.interfaces;

import com.hariofspades.dagger2advanced.model.ApiResponse;
import com.hariofspades.dagger2advanced.model.RandomUsers;
import com.hariofspades.dagger2advanced.model.UserDetails;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Hari on 20/11/17.
 */

public interface RandomUsersApi {
    @GET("api")
    Call<RandomUsers> getRandomUsers(@Query("results") int size);

    @POST("oauth/token")
    Call<Object> getToken(@HeaderMap Map<String, String> headers);

    @POST("account/join")
    Call<Object> join(@HeaderMap Map<String, String> headers, @Body UserDetails userDetails);
}