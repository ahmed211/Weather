package com.example.ahmed.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ahmed on 6/25/2017.
 */

public interface ApiInterface {

    @GET("forecast/daily")
    Call<ListWrapper<WeeklyModel>> getData( @Query("q") String cityName,
                                            @Query("APPID") String appId);
}
