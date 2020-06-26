package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MakeSchedule extends AppCompatActivity {

    private String activity;
    private String critical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_schedule);

        // Populate the fields based on the Intent Extra's
        Intent intent = getIntent();
        critical = intent.getStringExtra(AppConfig.EXTRA_CRITICAL);
        activity = intent.getStringExtra(AppConfig.EXTRA_ACTIVITY);
    }

    public void saveSchedule(View view) {
        // Get a shared preferences handle for the application
        SharedPreferences sharedPref = this.getSharedPreferences(
                AppConfig.FILE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        // Set the configuration values and commit
        editor.putString(AppConfig.EXTRA_CRITICAL, critical);
        editor.putString(AppConfig.EXTRA_ACTIVITY, activity);
        editor.commit();
    }
}
