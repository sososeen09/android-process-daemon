package com.sososeen09.process.daemon.sample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sososeen09.process.daemon.sample.service.GrayService;
import com.sososeen09.process.daemon.sample.service.NormalService;
import com.sososeen09.process.daemon.sample.service.WakeReceiver;
import com.sososeen09.process.daemon.sample.service.WhiteService;

public class ServiceKeepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_keep);
    }


    public void normal(View view) {
        startService(new Intent(this, NormalService.class));
    }

    public void white(View view) {

        startService(new Intent(this, WhiteService.class));
    }

    public void gray(View view) {

        startService(new Intent(this, GrayService.class));
    }

    public void black(View view) {
        Intent intent = new Intent(WakeReceiver.ACTION_WAKE);
//        sendBroadcast(intent);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 10000, broadcast);
//        Process.killProcess(Process.myPid());
//        System.exit(0);
    }
}
