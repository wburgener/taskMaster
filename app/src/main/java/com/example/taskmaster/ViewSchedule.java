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
    int i;
    int howMany;
    String task;
    int complete;
    private int notificationId;
    private String notifyId;
    SharedPreferences sched;
    Schedule schedule;
    Date date;
    public static final String MyPREFERENCES2 = "MyPrefsSchedule" ;
    private SimpleDateFormat dateFormatMonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);
        dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
        notificationId = 1;
        i = 0;
        complete = 0;
        notifyId = "one";
        sched = getSharedPreferences(MyPREFERENCES2, MODE_PRIVATE);
       //get our gson from make schedule
        Gson gson = new Gson();
        String json = sched.getString("schedule", "");
        schedule = gson.fromJson(json, Schedule.class);
        Date date1 = checkDate(schedule.getDate());
        date = java.util.Calendar.getInstance().getTime();
        createNotificationChannel();
        display(schedule);
        }



    void display(final Schedule schedule)
    {

        howMany = schedule.list.size();
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        //displaying the items and the complete buttons
        for (i = 0; i < howMany; i++)
        {
            TextView textView = new TextView(this);
            task = schedule.list.get(i).task;
            textView.setText(task);
            textView.setId(i);
            linearLayout.addView(textView);

            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            final Button button = new Button(this);
            button.setText("Complete");
            button.setId(i);
            linearLayout.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                //mark the item as true and shows percent
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Item Complete. You Did It!", Toast.LENGTH_LONG).show();
                    schedule.list.get(button.getId()).complete = true;
                    complete++;
                    Progress(view, schedule, complete, howMany);
                }
            });
            linearLayout.setOrientation(LinearLayout.VERTICAL);
        }
    }
//Makes sure we are dealing with the day in question
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

//progress runnable, takes the amount completed and how many items
    public void Progress(View view, Schedule schedule, int complete, int howMany)
    {
        TextView textView1 = (TextView) findViewById(R.id.percent);
        ProgressBar p = new ProgressBar(schedule, complete, howMany, textView1, this);

        Thread threadProgress = new Thread (p, "progress bar");
        threadProgress.start();
    }

    //our notification codes
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

    //notification channel
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

    //make sure we're saving the activity
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}





