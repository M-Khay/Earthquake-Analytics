package com.exercise.coding.ebay.earthquakeanalytics.rest;

import com.exercise.coding.ebay.earthquakeanalytics.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kushahuja on 7/15/17.
 */

public class APIClient {

    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
