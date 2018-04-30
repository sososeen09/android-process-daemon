package com.sososeen09.process.daemon.sample.keepliveactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.lang.ref.WeakReference;

/**
 * Created by yunlong.su on 2018/3/16.
 */

public class KeepLiveManager {
    private static final KeepLiveManager ourInstance = new KeepLiveManager();

    public static KeepLiveManager getInstance() {
        return ourInstance;
    }

    private WeakReference<Activity> mKeepAct;

    private KeepLiveManager() {
    }

    private KeepLiveReceiver mKeepLiveReceiver;

    public void registerKeepLifeReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mKeepLiveReceiver = new KeepLiveReceiver();
        context.registerReceiver(mKeepLiveReceiver, filter);
    }

    public void unregisterKeepLiveReceiver(Context context) {
        if (null != mKeepLiveReceiver) {
            context.unregisterReceiver(mKeepLiveReceiver);
        }
    }

    public void startKeepLiveActivity(Context context) {
        Intent intent = new Intent(context, KeepLiveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void startKeepLiveService(Context context) {
        Intent intent = new Intent(context, KeepLiveService.class);
        context.startService(intent);
    }

    public void finishKeepLiveActivity() {
        if (null != mKeepAct) {
            Activity activity = mKeepAct.get();
            if (null != activity) {
                activity.finish();
            }
            mKeepAct = null;
        }
    }

    public void setKeep(KeepLiveActivity keep) {
        mKeepAct = new WeakReference<Activity>(keep);
    }
}
