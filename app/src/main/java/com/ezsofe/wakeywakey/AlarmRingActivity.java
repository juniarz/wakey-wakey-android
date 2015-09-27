package com.ezsofe.wakeywakey;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.ezsofe.wakeywakey.R;
import com.ezsofe.wakeywakey.Voice.Offline.OfflineVoice;
import com.ezsofe.wakeywakey.Voice.Offline.OfflineVoiceManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

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
