package com.ezsofe.wakeywakey.Voice.Offline;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import com.ezsofe.wakeywakey.API.APIManager;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.http.Url;

/**
 * Created by Jia Rong on 9/27/2015.
 */
@EBean
public class OfflineVoiceManager {

    public static String LOG_TAG = OfflineVoiceManager.class.getSimpleName();

    Context context;
    Timer timer = new Timer();
    MediaPlayer ringtoneMp;
    MediaPlayer mp;
    Iterator<OfflineVoice> voiceQueueIterator;

    public OfflineVoiceManager(Context context) {
        this.context = context;
    }

    public void setRingtoneMp(MediaPlayer ringtoneMp) {
        this.ringtoneMp = ringtoneMp;
    }

    /**
     * Time sensitive, reload automatically
     * Download the list (skip listened) async
     */
    public void LoadList() {
        try {
            List<OfflineVoice> voiceList = APIManager.getService().getOfflineVoices().execute().body().body;

            if (timer != null) {
                timer.cancel();
                timer.purge();
                timer = new Timer();
            }

            if (voiceList.isEmpty()) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        LoadList();
                    }
                }, 1000 * 60);
                return;
            }
            Queue<OfflineVoice> voiceQueue = new LinkedList<>(voiceList);
            voiceQueueIterator = voiceQueue.iterator();
            playNextAudio();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to load offline voice list.", e);
        }
    }

    /**
     * Play audio with delay from list
     */
    void playNextAudio() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ringtoneMp.setVolume(1f, 1f);
                    Thread.sleep(new Random().nextInt(4000) + 1000);

                    if (!voiceQueueIterator.hasNext()) {
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                LoadList();
                            }
                        }, 1000 * 60);
                        return;
                    }

                    OfflineVoice offlineVoice = voiceQueueIterator.next();
                    if (mp != null) {
                        mp.release();
                    }

                    try {
                        mp = new MediaPlayer();

                        InputStream inputStream = new URL(offlineVoice.url).openStream();

                        final File tempFile = File.createTempFile(offlineVoice._id, ".3gp", context.getCacheDir());
                        tempFile.deleteOnExit();

                        FileOutputStream outputStream = new FileOutputStream(tempFile);

                        byte buffer[] = new byte[1024];
                        int length = 0;
                        while ((length = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, length);
                        }

                        mp.setDataSource(tempFile.getAbsolutePath());
                        mp.setAudioStreamType(AudioManager.STREAM_ALARM);
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                playNextAudio();
                            }
                        });
                        mp.prepare();
                        mp.start();
                        ringtoneMp.setVolume(0.1f, 0.1f);
                    } catch (IllegalStateException e) {
                        Log.e(LOG_TAG, "Failed to play voicelist.", e);
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Failed to play voicelist.", e);
                    }
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Failed to play voicelist.", e);
                }
            }
        });

        thread.start();
    }

    void pause() {

    }

    void resume() {

    }

    public void release() {
        if (mp != null) {
            mp.release();
        }

        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

}
