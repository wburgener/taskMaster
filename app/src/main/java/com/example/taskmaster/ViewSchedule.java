package com.example.taskmaster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;


import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class ViewSchedule extends AppCompatActivity {

    View view;
    int i = 0;
    String task;
    Boolean complete;
    final Schedule schedule = new Schedule();
    private int notificationId = 1;
    ArrayList<LocalTime> timeList = new ArrayList<LocalTime>();
    LocalTime rightnow;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);
        display();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void display()
    {

        int howMany = schedule.list.size();
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Time();
        for (i = 0; i < howMany; i++)
        {
            TextView textView = new TextView(this);
            task = schedule.list.get(i).task;
            TextViewFactory tvfactory = new TextViewFactory();
            textView = tvfactory.getTextView(i, task, textView);
            linearLayout.addView(textView);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            complete = schedule.list.get(i).complete;
            Button button = new Button(this);
            ButtonFactory bfactory = new ButtonFactory();
            button = bfactory.getButton(i, button);
            linearLayout.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Item Complete. You Did It!", Toast.LENGTH_LONG).show();
                    schedule.list.get(i).complete = true;
                    Progress(view);
                }
            });
            linearLayout.setOrientation(LinearLayout.VERTICAL);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Time()
    {
        LocalTime st = schedule.startTime;
        LocalTime time = st;
        int howMany = schedule.list.size();
        for (i = 0; i < howMany; i++)
        {
            long offset = schedule.list.get(i).length;
            time = st.plusHours(offset);
            timeList.add(i, time);

            if (rightnow.now() == time)
            {

            }
        }

    }


    public void Progress(View view)
    {
        ProgressBar p = new ProgressBar(schedule, this);
        Thread threadProgress = new Thread (p, "progress bar");
        threadProgress.start();
    }

   /* public void Notify()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon()
                .setContentTitle("Your Next Task")
                .setContentText()
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManger =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManger.notify(notificationId, builder.build());

    }

    */












}
