package com.example.week4_day3hw;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class ForegroundMusicService extends Service {

    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";

    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";

    public static final String ACTION_PAUSE = "ACTION_PAUSE";

    public static final String ACTION_PLAY = "ACTION_PLAY";

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
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),uri);
            switch(strAction){
                case ACTION_START_FOREGROUND_SERVICE:
                    startForegroundService();
                    Log.d("TAG", "Foreground service started!");
                    break;
                case ACTION_STOP_FOREGROUND_SERVICE:
                    stopForegroundService();
                    Log.d("TAG", "Foreground service stopped!");
                    break;
                case ACTION_PLAY:
                    ringtone.play();
                    Log.d("TAG", "Play button hit!");
                    break;
                case ACTION_PAUSE:
                    ringtone.stop();
                    Log.d("TAG", "Pause button hit");
                    break;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void startForegroundService(){

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notifyBuilder;
        String CHANNEL_ID = "music_player";

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "MusicPlayer",
                NotificationManager.IMPORTANCE_DEFAULT);

        channel.setDescription("Music Player Notification");
        manager.createNotificationChannel(channel);

        notifyBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);

        notifyBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Music Player")
                .setContentText("Play the music")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        // Set the intent that will fire when the user taps the notification
        Intent playIntent = new Intent(this, ForegroundMusicService.class);
        playIntent.setAction(ACTION_PLAY);
        PendingIntent pendingPlayIntent = PendingIntent.getService(this, 0, playIntent, 0);
        NotificationCompat.Action actionPlay = new NotificationCompat.Action( R.drawable.ic_play_arrow_black_24dp, "Play", pendingPlayIntent);
        notifyBuilder.addAction(actionPlay);

        Intent pauseIntent = new Intent(this, ForegroundMusicService.class);
        pauseIntent.setAction(ACTION_PAUSE);
        PendingIntent pendingPauseIntent = PendingIntent.getService(this, 0, pauseIntent, 0);
        NotificationCompat.Action actionPause = new NotificationCompat.Action(R.drawable.ic_pause_black_24dp, "Pause", pendingPauseIntent);
        notifyBuilder.addAction(actionPause);
        Notification notify = notifyBuilder.build();
        startForeground(1, notify);

    }

    private void stopForegroundService(){

        Log.d("TAG", "Foreground service stopped");
        stopForeground(true);
        stopSelf();

    }
}
