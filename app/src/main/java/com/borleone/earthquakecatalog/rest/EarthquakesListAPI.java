package com.borleone.earthquakecatalog.rest;

import com.borleone.earthquakecatalog.model.EarthquakesListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Borle on 6/16/2018.
 */
public interface EarthquakesListAPI {
    @GET("query")
    Call<EarthquakesListResponse> getList(@Query("format") String format,
                                                    @Query("starttime") String startTime,
                                                    @Query("minmagnitude") String minMag);
}
