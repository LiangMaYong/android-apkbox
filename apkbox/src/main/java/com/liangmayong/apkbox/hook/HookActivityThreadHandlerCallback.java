package com.liangmayong.apkbox.hook;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.liangmayong.apkbox.hook.activity.HookActivity_LaunchActivity;
import com.liangmayong.apkbox.reflect.ApkReflect;

/**
 * Created by LiangMaYong on 2016/9/19.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public final class HookActivityThreadHandlerCallback implements Handler.Callback {

    private final Handler mBase;
    private int LAUNCH_ACTIVITY = 100;

    public HookActivityThreadHandlerCallback(Handler base) {
        mBase = base;
        try {
            LAUNCH_ACTIVITY = (Integer) ApkReflect.getField(Class.forName("android.app.ActivityThread$H"), null, "LAUNCH_ACTIVITY");
        } catch (Exception e) {
        }
    }

    @Override
    public boolean handleMessage(final Message msg) {
        if (msg.what == LAUNCH_ACTIVITY) {
            // LAUNCH_ACTIVITY
            HookActivity_LaunchActivity.handleLaunchActivity(msg);
        }
        mBase.handleMessage(msg);
        return true;
    }
}
