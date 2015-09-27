package com.ezsofe.wakeywakey.API;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Jia Rong on 9/26/2015.
 */
public class S3Manager {

    public static S3Service getService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(S3Service.BASE_URL)
                .build();

        S3Service service = retrofit.create(S3Service.class);

        return service;
    }
}
