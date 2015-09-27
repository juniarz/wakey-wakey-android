package com.ezsofe.wakeywakey.API;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by Jia Rong on 9/26/2015.
 */
public interface S3Service {

        String BASE_URL = "https://wakey-wakey-offline-dev.s3.amazonaws.com/";

        /**
         * @param url :signed s3 url string after 'BASE_URL'.
         * @param file :file to upload.
         */
        @PUT("{url}")
        Call<ResponseBody> uploadFile(@Path(value = "url") String url, @Body() RequestBody file);
}
