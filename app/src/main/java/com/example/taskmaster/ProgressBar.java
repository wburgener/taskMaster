package com.example.taskmaster;

import android.app.Activity;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class ProgressBar implements Runnable {
    Schedule schedule;
    private Activity activity;

    private WeakReference<Activity> activityRef;

    public ProgressBar(Schedule schedule, Activity activity)
    {
        this.activity = activity;
        this.schedule = schedule;

    }

    public void run()
    {
        int total = schedule.list.size();
        int complete = 0;
        for (int i = 0; i < total; i++)
        {
            if (schedule.list.get(i).complete == true)
            {
                complete++;
            }
        }
        final int percent = complete / total;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = (TextView) activity.findViewById(R.id.percent);
                textView.setText("You have completed " + percent + "% of items!");
            }


        });


    }
}
