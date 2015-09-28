package com.ezsofe.wakeywakey;

import android.content.Intent;
import android.speech.tts.Voice;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ezsofe.wakeywakey.API.APIManager;
import com.ezsofe.wakeywakey.User.UserManager;
import com.ezsofe.wakeywakey.Voice.VoiceManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.bson.types.ObjectId;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    VoiceManager voiceManager = new VoiceManager();
    Button recordBtn;
    boolean isRecording = false;

    @AfterViews
    void initRecordButton() {
        recordBtn=(Button)findViewById(R.id.record_btn);
        recordBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if (!isRecording) {
                            voiceManager.startRecording();
                            isRecording = true;
                            recordBtn.setText("RECORDING...");
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        recordBtn.setText("UPLOADING...");
                        stopRecording();
                        isRecording = false;
                        recordBtn.setText("RECORD");
                        break;
                }
                return false;
            }
        });
    }

    @AfterViews
    void showLoggedInUser() {
        TextView tv = (TextView) findViewById(R.id.textView2);
        tv.setText("Currently logged-in user: " + UserManager.currentLoggedInUser.toString());
    }

    @Background
    void stopRecording() {
        voiceManager.stopRecording();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_compose:
                Intent intent = new Intent(this, AlarmRingActivity_.class);
                this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
