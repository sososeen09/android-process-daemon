package com.sososeen09.process.daemon.sample.service;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sososeen09.process.daemon.sample.util.ProcessUtils;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;


/**
 * Created by yunlong.su on 2018/3/16.
 */

public class WakeReceiver extends BroadcastReceiver {
    private final static int NOTIFICATION_ID = 1001;
    public final static String ACTION_WAKE = "com.sososeen09.wake";
    private final static String TAG = "WakeReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals(ACTION_WAKE)) {
            context.startService(new Intent(context, WakeService.class));

            Log.e(TAG, "onReceive: " + "收到广播，兄弟们要起来了。。。");
        }
    }


    public static class WakeService extends Service {
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            try {
                Notification notification = new Notification();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    startForeground(NOTIFICATION_ID, notification);
                } else {
                    startForeground(NOTIFICATION_ID, notification);
                    // start InnerService
                    startService(new Intent(this, WakeInnerService.class));
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }

            Log.e(TAG, "onReceive: " + "我是 WakeService，我起来了，谢谢兄弟。。。" + ProcessUtils.getProcessName(this));

            return super.onStartCommand(intent, flags, startId);

        }
    }

    public static class WakeInnerService extends Service {

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }


        @Override
        public void onCreate() {
            super.onCreate();
            try {
                startForeground(NOTIFICATION_ID, new Notification());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            stopSelf();
        }

        @Override
        public void onDestroy() {
            stopForeground(true);
            super.onDestroy();
        }
    }

}
