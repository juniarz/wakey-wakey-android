package com.ezsofe.wakeywakey;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;

import com.ezsofe.wakeywakey.API.APIManager;
import com.ezsofe.wakeywakey.R;
import com.ezsofe.wakeywakey.Voice.Offline.OfflineVoice;
import com.ezsofe.wakeywakey.Voice.Offline.OfflineVoiceManager;
import com.squareup.okhttp.ResponseBody;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import java.io.IOException;

import retrofit.Response;

@EActivity(R.layout.activity_alarm_ring)
public class AlarmRingActivity extends ActionBarActivity {

    @Bean
    OfflineVoiceManager manager;

    MediaPlayer mp;

    @AfterViews
    void showHomeAsUp() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        mp = MediaPlayer.create(getApplicationContext(), notification);
        mp.start();

        manager = new OfflineVoiceManager(this);
        manager.setRingtoneMp(mp);

        test();
    }

    @Background
    public void onClearBtnClicked(View view) {
        // TODO: ADMIN ONLY, remove hardcode after users is implemented.
        try {
            Response<ResponseBody> response = APIManager.getService().clearOfflineVoiceListened("56045ed6205cf8a877067234").execute();
            if (response.isSuccess()) {
                manager.release();
                manager = new OfflineVoiceManager(this);
                manager.setRingtoneMp(mp);
                manager.LoadList();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                release();
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Background
    void test() {
        manager.LoadList();
    }

    public void onBackPressed() {
        super.onBackPressed();

        release();
    }

    private void release() {
        mp.release();
        manager.release();
    }
}
