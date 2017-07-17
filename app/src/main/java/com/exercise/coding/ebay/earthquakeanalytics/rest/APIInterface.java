package com.exercise.coding.ebay.earthquakeanalytics.rest;

import com.exercise.coding.ebay.earthquakeanalytics.data.model.EarthquakeListData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by kushahuja on 7/15/17.
 */

public interface APIInterface {

        @GET("earthquakesJSON")
        Call<EarthquakeListData> getRecentEarthquakeList(@Query("north") String north,@Query("south") String south,@Query("east") String east,@Query("west") String west,@Query("username") String username);

//        @GET("movie/{id}")
//        Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
//    }
}