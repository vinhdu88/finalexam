package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private ImageView backgroundImageView;
    private Button buttonCalendar;
    private TextClock textClock;
    private BroadcastReceiver timeChangedReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonCalendar = findViewById(R.id.buttonCalendar);
        backgroundImageView = findViewById(R.id.backgroundImageView);
        textClock= findViewById(R.id.textClock);


        buttonCalendar.setOnClickListener(view -> openSecondActivity());
        Log.d(MainActivity.class.getName(), "onCreate");


        updateBackground();
        timeChangedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateBackground();
            }
        };

    }

    private void openSecondActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
    private void updateBackground() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        Drawable backgroundDrawable;

        if (hour >= 6 && hour < 18) {
            backgroundDrawable = getResources().getDrawable(R.drawable.sun);
        } else {
            backgroundDrawable = getResources().getDrawable(R.drawable.moon);
        }

        backgroundImageView.setImageDrawable(backgroundDrawable);
    }
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(timeChangedReceiver, intentFilter);
    }
    protected void onPause() {
        super.onPause();
        unregisterReceiver(timeChangedReceiver);
    }
}
