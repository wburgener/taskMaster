package com.example.taskmaster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.util.Calendar;


public class ViewSchedule extends AppCompatActivity {

    View view;
    int i = 0;
    String task;
    Boolean complete;
    final Schedule schedule = new Schedule();
    private int notificationId = 1;
    private String notifyId = "one";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);
        createNotificationChannel();
        display();
    }


    void display()
    {

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

   public void Notify()
    {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, notifyId)
                .setSmallIcon(R.drawable.taskmasterimage)
                .setContentTitle("To Do Today")
                .setContentText("Check what you've done so far")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManger =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManger.notify(notificationId, builder.build());

    }

    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = getString(R.string.NotifyChannel);
            String description = getString(R.string.NotfyChannelD);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(notifyId, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



   public void Alarm() {
       SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
       if (!prefs.getBoolean("firstTime", false)) {

           Intent alarmIntent = new Intent(this, AlarmReceiver.class);
           PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

           AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

           Calendar calendar = Calendar.getInstance();
           calendar.setTimeInMillis(System.currentTimeMillis());
           calendar.set(Calendar.HOUR_OF_DAY, 12);
           calendar.set(Calendar.MINUTE, 0);
           calendar.set(Calendar.SECOND, 1);

           manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

           SharedPreferences.Editor editor = prefs.edit();
           editor.putBoolean("firstTime", true);
           editor.apply();
       }
   }

    public class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Notify();
        }
    }
}





