package com.example.week4_day3hw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    IntentFilter intentFilterAirplane = new IntentFilter("android.intent.action.AIRPLANE_MODE");
    IntentFilter intentFilterWifi = new IntentFilter("android.net.wifi.STATE_CHANGE");
    IntentFilter intentFilterScreenOn = new IntentFilter("android.intent.action.SCREEN_ON");
    IntentFilter intentFilterScreenOff = new IntentFilter("android.intent.action.SCREEN_OFF");
    IntentFilter intentFilterBT = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");

    TextView tvAirplane;
    TextView tvWifiState;
    TextView tvScreen;
    TextView tvBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvAirplane = findViewById(R.id.tvAirplane);
        tvWifiState = findViewById(R.id.tvWifiState);
        tvScreen = findViewById(R.id.tvScreenState);
        tvBluetooth = findViewById(R.id.tvBluetooth);

        if (isAirplaneModeOn(getApplicationContext())) {
            tvAirplane.setText("Airplane Mode is On");
        } else {
            tvAirplane.setText("Airplane Mode is Off");
        }
        BroadcastReceiver airplaneReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (isAirplaneModeOn(getApplicationContext())) {
                    tvAirplane.setText("Airplane Mode is On");
                } else {
                    tvAirplane.setText("Airplane Mode is Off");
                }
            }
        };

        BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (isWifiOn(getApplicationContext())) {
                    tvWifiState.setText("WiFi set On by User");
                } else {
                    tvWifiState.setText("WiFi set Off by User");
                }
            }
        };

        BroadcastReceiver screenOnReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                tvScreen.setText("Screen On");
                Log.d("TAG", "Screen is On"); //logs this since the log for off could confuse
            }
        };

        BroadcastReceiver screenOffReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                tvScreen.setText("Screen Off");//won't show because when it's off it won't be vis
                Log.d("TAG", "Screen is off"); //logs this since the screen's off
            }
        };

        if (isBTOn(getApplicationContext())) {
            tvBluetooth.setText("Bluetooth is On");
        } else{
            tvBluetooth.setText("Bluetooth is Off");
        }

        BroadcastReceiver BluetoothPress = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (isBTOn(getApplicationContext())) {
                    tvBluetooth.setText("Bluetooth is On");
                } else{
                    tvBluetooth.setText("Bluetooth is Off");
                }
            }
        };


        getApplicationContext().registerReceiver(airplaneReceiver, intentFilterAirplane);
        getApplicationContext().registerReceiver(wifiReceiver, intentFilterWifi);
        getApplicationContext().registerReceiver(screenOnReceiver, intentFilterScreenOn);
        getApplicationContext().registerReceiver(screenOffReceiver, intentFilterScreenOff);
        getApplicationContext().registerReceiver(BluetoothPress, intentFilterBT);

    }

    private static boolean isAirplaneModeOn(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    private static boolean isWifiOn(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.WIFI_ON, 0) != 0;
    }

    private  static boolean isBTOn(Context context){
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.BLUETOOTH_ON, 0) != 0;
    }
}
