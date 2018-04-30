package com.sososeen09.process.daemon.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sososeen09.process.daemon.sample.account.AccountHelper;
import com.sososeen09.process.daemon.sample.doubleprocess.LocalService;
import com.sososeen09.process.daemon.sample.doubleprocess.RemoteService;
import com.sososeen09.process.daemon.sample.jobschedule.MyJobService;
import com.sososeen09.process.daemon.sample.keepliveactivity.KeepLiveManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void keepLiveActivity(View view) {
        KeepLiveManager.getInstance().registerKeepLifeReceiver(this);
    }

    public void keepService(View view) {
        startActivity(new Intent(this, ServiceKeepActivity.class));
    }

    public void addAccount(View view) {
        AccountHelper.addAccount(this);
        AccountHelper.autoSync();
    }

    public void jobSchedule(View view) {
        MyJobService.startJob(this);
    }

    public void doubleProcess(View view) {
        startService(new Intent(this, LocalService.class));
        startService(new Intent(this, RemoteService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeepLiveManager.getInstance().unregisterKeepLiveReceiver(this);
    }
}
