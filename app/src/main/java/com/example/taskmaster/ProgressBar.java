package com.example.taskmaster;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

// our progress percent code
public class ProgressBar implements Runnable {
    private Schedule schedule;
    private Activity activity;
    int percent;
    int complete;
    int howMany;
    TextView textView;


    public ProgressBar(Schedule schedule, int complete, int howMany, TextView textView, Activity activity)
    {
        this.activity = activity;
        this.schedule = schedule;
        this.complete = complete;
        this.howMany =  howMany;
        this.textView = textView;

    }

    public void run()
    {

        percent = complete / howMany;
        final String text = "You have completed " + percent + "% of items!";

        //making sure we can actually see the toast
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //textView.setText(text);
               Toast toast = Toast.makeText(activity, text, Toast.LENGTH_SHORT);
               toast.show();
            }


        });


    }
}
