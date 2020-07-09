package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.util.Date;
import java.util.Scanner;


public class ViewSchedule extends AppCompatActivity {

    View view;
    int i = 0;
    String task;
    Boolean complete;
    final Schedule schedule = new Schedule();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);
    }

    void display()
    {

        int startTime = schedule.startTime;
        int howMany = schedule.list.size();
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
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

    public void Progress(View view)
    {
        ProgressBar p = new ProgressBar(schedule, this);
        Thread threadProgress = new Thread (p, "progress bar");
        threadProgress.start();
    }







}
