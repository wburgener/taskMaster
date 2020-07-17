package com.example.taskmaster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ViewSchedule extends AppCompatActivity {

    View view;
    int i = 0;
    String task;
    Boolean complete;
    private int notificationId = 1;
    private String notifyId = "one";
    final SharedPreferences sched = getPreferences(MODE_PRIVATE);
    Schedule schedule;
    Date date;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);
        Gson gson = new Gson();
        String json = sched.getString("schedule", "");
        schedule = gson.fromJson(json, Schedule.class);
        Date date1 = checkDate(schedule.date);
        date = java.util.Calendar.getInstance().getTime();
        if (date.equals(date1)) {

            createNotificationChannel();
            display(schedule);
        }
    }


    void display(final Schedule schedule)
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

    public Date checkDate(String date) {
        String regex = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
        //Creating a pattern object
        Pattern pattern = Pattern.compile(regex);
        //Matching the compiled pattern in the String
        Matcher matcher = pattern.matcher(date);
        boolean bool = matcher.matches();
        if (bool) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dt = null;
            try {
                dt = df.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return dt;
        }
        else return null;
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


//alarm for notifications
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

   //alarm for destroying activity at midnight
    public void Alarm1() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {

            Intent alarmIntent = new Intent(this, AlarmReceiver1.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
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

    public class AlarmReceiver1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}





