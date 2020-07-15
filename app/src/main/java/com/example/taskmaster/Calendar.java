package com.example.taskmaster;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calendar extends AppCompatActivity {

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        final SharedPreferences settings = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        final Map<String, ?> events = settings.getAll();
        // Set an event for 4th of July
        for (Map.Entry<String, ?> entry : events.entrySet())
        {
            Set<String> eventValue = (Set<String>) entry.getValue();
            //String date = eventValue.iterator().next();
            String[] arrayOfString = new String[eventValue.size()];

            // Copy elements from set to string array
            // using advanced for loop
            int index = 0;
            for (String str : eventValue) {
                arrayOfString[index++] = str;
            }

            for(String checkString : arrayOfString) {
                String regex = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
                //Creating a pattern object
                Pattern pattern = Pattern.compile(regex);
                //Matching the compiled pattern in the String
                Matcher matcher = pattern.matcher(checkString);
                boolean bool = matcher.matches();
                if(bool) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date dt = null;
                    try {
                        dt = df.parse(checkString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Long l = dt.getTime();
                    Event ev1 = new Event(Color.BLUE, l);
                    compactCalendar.addEvent(ev1);
                }
            }
        }




        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String format = formatter.format(dateClicked);
                List eventsOnDate = new ArrayList<String>();
                for (Map.Entry<String, ?> entry : events.entrySet())
                {
                    Set<String> eventValues = (Set<String>) entry.getValue();
                    String[] arrayOfString = new String[eventValues.size()];

                    for (String str : eventValues) {
                        String regex = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
                        //Creating a pattern object
                        Pattern pattern = Pattern.compile(regex);
                        //Matching the compiled pattern in the String
                        Matcher matcher = pattern.matcher(str);
                        boolean bool = matcher.matches();
                        if(bool){
                            if(format.equals(str))
                            {
                                eventsOnDate.add(entry.getKey());
                            }
                        }

                    }

                }
                if (eventsOnDate.size() == 0){
                    Toast.makeText(context, "No events on this day", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, eventsOnDate.toString(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }
}