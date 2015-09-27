package com.ezsofe.wakeywakey.API;

import com.ezsofe.wakeywakey.API.Response.APIResponse;
import com.ezsofe.wakeywakey.API.Response.SignOfflineVoiceURLResponse;
import com.ezsofe.wakeywakey.Voice.Offline.OfflineVoice;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Jia Rong on 9/26/2015.
 */
public interface APIService {
    String BASE_URL = "http://wakey-wakey-api.herokuapp.com/";

    //TODO: Remove hardcode after user is implemented
    @GET("offline_voice")
    Call<APIResponse<List<OfflineVoice>>> getOfflineVoices();//@Query("user_id") String user_id);

    //TODO: Remove hardcode after user is implemented
    @GET("offline_voice/sign?user_id=5606706a9624c0a085046e60")
    Call<APIResponse<SignOfflineVoiceURLResponse>> signOfflineVoiceURL(@Query("file_ext") String file_ext); //@Query("user_id") String user_id, @Query("file_ext") String file_ext);

    @GET("offline_voice/published")
    Call<ResponseBody> setOfflineVoicePublished(@Query("_id") String _id);
}
