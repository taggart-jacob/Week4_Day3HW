package com.example.week4_day3hw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForegroundMusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_music);
    }

    public void onClickStart(View view) {
        Intent intentStart = new Intent(this, ForegroundMusicService.class);
        intentStart.setAction(ForegroundMusicService.ACTION_START_FOREGROUND_SERVICE);
        startService(intentStart);
    }

    public void onClickStop(View view) {
        Intent intentStop = new Intent(this, ForegroundMusicService.class);
        intentStop.setAction(ForegroundMusicService.ACTION_STOP_FOREGROUND_SERVICE);
        stopService(intentStop);
    }
}
