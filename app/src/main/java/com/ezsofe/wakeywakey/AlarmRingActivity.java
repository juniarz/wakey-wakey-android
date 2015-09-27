package com.ezsofe.wakeywakey;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.ezsofe.wakeywakey.R;

public class AlarmRingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ring);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
