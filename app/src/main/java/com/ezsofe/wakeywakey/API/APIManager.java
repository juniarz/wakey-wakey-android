package com.ezsofe.wakeywakey.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Jia Rong on 9/26/2015.
 */
public class APIManager {

    private static final String MONGO_UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static APIService getService() {

        Gson gson = new GsonBuilder().setDateFormat(MONGO_UTC_FORMAT).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService service = retrofit.create(APIService.class);

        return service;
    }
}
