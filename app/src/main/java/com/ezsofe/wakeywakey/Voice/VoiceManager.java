package com.ezsofe.wakeywakey.Voice;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.ezsofe.wakeywakey.API.APIManager;
import com.ezsofe.wakeywakey.API.Response.APIResponse;
import com.ezsofe.wakeywakey.API.Response.SignOfflineVoiceURLResponse;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Jia Rong on 9/26/2015.
 */
public class VoiceManager {

    public static String LOG_TAG = VoiceManager.class.getSimpleName();

    MediaRecorder mRecorder;
    public String mFileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Wakey Wakey/temp/";
    public String mFileName = UUID.randomUUID() + ".3gp";

    public void startRecording() {

        File fileDir = new File(mFileDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File file = new File(mFileDir, mFileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.e(LOG_TAG, "createNewFile() failed", e);
            }
        }

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileDir + mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed", e);
        }

        mRecorder.start();
    }

    public void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        uploadVoice();
    }

    public void uploadVoice() {
        try {
            APIResponse<SignOfflineVoiceURLResponse> signOfflineVoiceURLResponse = APIManager.getService().signOfflineVoiceURL("3gp").execute().body();

            String url = signOfflineVoiceURLResponse.body.url;

            final File file = new File(mFileDir + mFileName);
            try {
                Response response = uploadToS3(url, file);
                if (response.isSuccessful()) {
                    APIManager.getService().setOfflineVoicePublished(signOfflineVoiceURLResponse.body.offlineVoice_id).execute().body();
                } else {
                    Log.e(LOG_TAG, "Upload to S3 failed." + response.headers().toString());
                }

            } catch (Exception e) {
                Log.e(LOG_TAG, "uploadVoice() failed", e);
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "uploadVoice() failed", e);
        }
    }

    public Response uploadToS3(String url, File file) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .put(RequestBody.create(null, file))
                .build();

        Response response = client.newCall(request).execute();

        return response;
    }
}
