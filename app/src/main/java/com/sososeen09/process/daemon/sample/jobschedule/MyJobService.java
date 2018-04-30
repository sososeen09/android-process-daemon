package com.sososeen09.process.daemon.sample.jobschedule;

import android.annotation.SuppressLint;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.app.job.JobWorkItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sososeen09.process.daemon.sample.doubleprocess.LocalService;
import com.sososeen09.process.daemon.sample.doubleprocess.RemoteService;
import com.sososeen09.process.daemon.sample.util.ProcessUtils;

import java.util.List;

@SuppressLint("NewApi")
public class MyJobService extends JobService {
    private static final String TAG = "MyJobService";

    public static void startJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo.Builder builder = new JobInfo.Builder(10, new ComponentName(context.getPackageName(), MyJobService.class.getName())).setPersisted(true);

        //小于7.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            // 每隔1s 执行一次 job
            builder.setPeriodic(1_000);
        } else {
            //延迟执行任务
            builder.setMinimumLatency(1_000);
        }

        if (jobScheduler != null) {
            jobScheduler.schedule(builder.build());
        }
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG, "start job schedule");
        //如果7.0以上 轮训
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            startJob(this);
        }

        //JobSchedule结合双进程守护
        boolean isLocalRun = ProcessUtils.isRunningService(this, LocalService.class.getName());
        boolean isRemoteRun = ProcessUtils.isRunningService(this, RemoteService.class.getName());
        if (!isLocalRun || !isRemoteRun) {
            startService(new Intent(this, LocalService.class));
            startService(new Intent(this, RemoteService.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
