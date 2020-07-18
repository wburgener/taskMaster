package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendToMakeSchedule(View view) {
        Intent intent = new Intent(this, MakeSchedule.class);
        startActivity(intent);
    }

    public void sendToViewSchedule(View view) {
        Intent intent = new Intent(this, ViewSchedule.class);
        startActivity(intent);
    }

    public void sendToCalendar(View view) {
        Intent intent = new Intent(this, Calendar.class);
        startActivity(intent);
    }

    public void sendToSchedules(View view) {
        Intent intent = new Intent(this, Schedules.class);
        startActivity(intent);
    }


}
