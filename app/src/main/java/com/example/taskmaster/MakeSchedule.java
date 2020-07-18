package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MakeSchedule extends AppCompatActivity {

    TextView activityText;
    Button b1;
    Button b2;
    DatePicker actDate;
    int i = 0;
    Spinner spinner;
    Schedule schedule;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String MyPREFERENCES2 = "MyPrefsSchedule" ;
    public static final String Activity = "activityKey";
    public static final String Schedule = "scheduleKey";
    SharedPreferences sharedpreferences;
    SharedPreferences sharedPreferences2;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "MakeSchedule";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_schedule);

        activityText = (TextView)findViewById(R.id.editText2);
        b1 = (Button)findViewById(R.id.button);
        b2 = (Button)findViewById(R.id.button5);
        spinner = (Spinner) findViewById(R.id.spinner);

        actDate = (DatePicker) findViewById(R.id.datePicker1);    //added by me

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences2 = getSharedPreferences(MyPREFERENCES2, Context.MODE_PRIVATE);
        schedule = new Schedule();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start ----PLEASE FEEL FREE TO CHANGE IT THE WAY YOU WANT....I JUST PUT THIS CODE TO MAKE IT WORK ON MY CALENDAR VIEW
                int day = actDate.getDayOfMonth();
                int month = actDate.getMonth();
                int year =  actDate.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); //PLEASE I NEED THE DATE IN THIS FORMAT IN THE SET<String>

                String date = format1.format(calendar.getTime());
                String activity  = activityText.getText().toString();
                String importance = spinner.getSelectedItem().toString();

                Todo todo = new Todo();
                //adding activity to schedule
                schedule.list.add(todo);
                schedule.list.get(i).setTask(activity);
                schedule.list.get(i).setImportance(importance);
                schedule.setDate(date);

                i++;

                SharedPreferences.Editor editor = sharedpreferences.edit();

                Set<String> set = new HashSet<>();
                set.add(activity);
                set.add(date);
                set.add(importance);


                editor.putStringSet(activity, set); //WESLY ...PLEASE MAKE SURE YOU USE PutStringSet ...so I can grab the Date in my calendar forloop
                //END

                editor.commit();

                Toast.makeText(MakeSchedule.this,"Data Saved",Toast.LENGTH_SHORT).show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences2.edit();
                Gson gson = new Gson();
                String json = gson.toJson(schedule);
                editor.putString("schedule", json);
                editor.commit();

                schedule.list.clear();
                i = 0;

            }
        });

        //mDisplayDate = (TextView) findViewById(R.id.Date);
       /* mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MakeSchedule.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy " + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        */

    }
}
