package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class MakeSchedule extends AppCompatActivity {

    private String activity;
    private String critical;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "MakeSchedule";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_schedule);
        mDisplayDate = (TextView) findViewById(R.id.Date);

        // Populate the fields based on the Intent Extra's
        Intent intent = getIntent();
        critical = intent.getStringExtra(AppConfig.EXTRA_CRITICAL);
        activity = intent.getStringExtra(AppConfig.EXTRA_ACTIVITY);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
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

//        public void saveSchedule(View view) {
//            // Get a shared preferences handle for the application
//            SharedPreferences sharedPref = this.getSharedPreferences(
//                    AppConfig.FILE_PREF, Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPref.edit();
//            // Set the configuration values and commit
//            editor.putString(AppConfig.EXTRA_CRITICAL, critical);
//            editor.putString(AppConfig.EXTRA_ACTIVITY, activity);
//            editor.commit();
//        }
    }
}
