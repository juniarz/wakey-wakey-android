package com.ezsofe.wakeywakey;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_add_alarm)
public class AddAlarmActivity extends ActionBarActivity {

    @AfterViews
    void afterViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
