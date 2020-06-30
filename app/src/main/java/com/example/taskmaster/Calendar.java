package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

public class Calendar extends AppCompatActivity {

//    private EditText activityId;
//    private EditText criticalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }

//    public void loadScheduleItem(View view) {
//        // Get a shared preferences handle for the application
//        SharedPreferences sharedPref = this.getSharedPreferences(
//                AppConfig.FILE_PREF, Context.MODE_PRIVATE);
//
//        // Get the configuration values
//        String book = sharedPref.getString(AppConfig.EXTRA_ACTIVITY, "");
//        String chapter = sharedPref.getString(AppConfig.EXTRA_CRITICAL, "");
//
//        // Populate the Text Fields
//        activityId.setText(book);
//        criticalId.setText(chapter);
//    }
}
