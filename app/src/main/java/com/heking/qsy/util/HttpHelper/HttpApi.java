package com.heking.qsy.util.HttpHelper;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Lee on 2017/10/18.
 */

public interface HttpApi {
    @GET
    Single<String> get(@Url String Url);
    @POST
    Single<String> post(@Url String Url);
    @GET
    Call<String> getString(@Url String Url);


}
