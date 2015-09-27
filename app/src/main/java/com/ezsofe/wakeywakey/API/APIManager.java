package com.ezsofe.wakeywakey.API;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Jia Rong on 9/26/2015.
 */
public class APIManager {

    public static APIService getService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        return service;
    }
}
