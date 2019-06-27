package com.example.week4_day3hw;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ForegroundMusicService extends Service {
    public ForegroundMusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        //returning anything but null unnecessary for this
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "onCreate...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null){
            String strAction = intent.getAction();

            switch(strAction){
                case "ACTION_START_FOREGROUND_SERVICE":
                    startForegroundService();
                    Log.d("TAG", "Foreground service started!");
                    break;
                case "ACTION_STOP_FOREGROUND_SERVICE":
                    stopForegroundService();
                    Log.d("TAG", "Foreground service stopped");
                    break;
                case "ACTION_PLAY":
                    Log.d("TAG", "Play button hit!");
                    break;
                case "ACTION_PAUSE":
                    Log.d("TAG", "Pause button hit");
                    break;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void startForegroundService(){

        //default intent for notification ***
        Intent intent = new Intent();

        PendingIntent pendIntent = PendingIntent.getActivity(this, 0, intent, 0);

        /*AlertDialog.Builder userAlertBuild = new AlertDialog.Builder(this);
        userAlertBuild.setTitle("Service started");
        userAlertBuild.setMessage("Music Player service has begun");*/

        //final AlertDialog dialog = userAlertBuild.create();

       /* userAlertBuild.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });

        dialog.show();*/

        Intent playIntent = new Intent(this, ForegroundMusicService.class);
        playIntent.setAction("ACTION_PLAY");
        PendingIntent pendingPlayIntent = PendingIntent.getService(this, 0, playIntent, 0);

        Intent pauseIntent = new Intent(this, ForegroundMusicService.class);
        pauseIntent.setAction("ACTION_PAUSE");
        PendingIntent pendingPauseIntent = PendingIntent.getService(this, 0, pauseIntent, 0);

    }

    private void stopForegroundService(){

    }
}
