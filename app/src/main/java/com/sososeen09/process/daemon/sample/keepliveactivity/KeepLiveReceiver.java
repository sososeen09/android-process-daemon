package com.sososeen09.process.daemon.sample.keepliveactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by yunlong.su on 2018/3/16.
 */

public class KeepLiveReceiver extends BroadcastReceiver {
    private static final String TAG = "KeepLiveReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e(TAG, "receive action:" + action);
        //屏幕关闭事件
        if (TextUtils.equals(action, Intent.ACTION_SCREEN_OFF)) {
            //关屏 开启1px activity
            KeepLiveManager.getInstance().startKeepLiveActivity(context);
            // 解锁事件
        } else if (TextUtils.equals(action, Intent.ACTION_USER_PRESENT)) {
            KeepLiveManager.getInstance().finishKeepLiveActivity();
        }

        KeepLiveManager.getInstance().startKeepLiveService(context);
    }
}
