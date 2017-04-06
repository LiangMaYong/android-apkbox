package com.liangmayong.apkbox.hook;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.liangmayong.apkbox.hook.handle.HookHandle_BindService;
import com.liangmayong.apkbox.hook.handle.HookHandle_CreateService;
import com.liangmayong.apkbox.hook.handle.HookHandle_LaunchActivity;
import com.liangmayong.apkbox.hook.handle.HookHandle_ServiceArgs;
import com.liangmayong.apkbox.hook.handle.HookHandle_StopService;
import com.liangmayong.apkbox.hook.handle.HookHandle_UnbindService;
import com.liangmayong.apkbox.reflect.ApkReflect;

/**
 * Created by LiangMaYong on 2016/9/19.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public final class HookActivityThreadHandlerCallback implements Handler.Callback {

    private final Handler mBase;
    private int LAUNCH_ACTIVITY = 100;
    private int CREATE_SERVICE = 114;
    private int SERVICE_ARGS = 115;
    private int STOP_SERVICE = 116;
    private int BIND_SERVICE = 121;
    private int UNBIND_SERVICE = 122;

    public HookActivityThreadHandlerCallback(Handler base) {
        mBase = base;
        try {
            LAUNCH_ACTIVITY = (Integer) ApkReflect.getField(Class.forName("android.app.ActivityThread$H"), null, "LAUNCH_ACTIVITY");
        } catch (Exception e) {
        }
        try {
            CREATE_SERVICE = (Integer) ApkReflect.getField(Class.forName("android.app.ActivityThread$H"), null, "CREATE_SERVICE");
        } catch (Exception e) {
        }
        try {
            SERVICE_ARGS = (Integer) ApkReflect.getField(Class.forName("android.app.ActivityThread$H"), null, "SERVICE_ARGS");
        } catch (Exception e) {
        }
        try {
            STOP_SERVICE = (Integer) ApkReflect.getField(Class.forName("android.app.ActivityThread$H"), null, "STOP_SERVICE");
        } catch (Exception e) {
        }
        try {
            BIND_SERVICE = (Integer) ApkReflect.getField(Class.forName("android.app.ActivityThread$H"), null, "BIND_SERVICE");
        } catch (Exception e) {
        }
        try {
            UNBIND_SERVICE = (Integer) ApkReflect.getField(Class.forName("android.app.ActivityThread$H"), null, "UNBIND_SERVICE");
        } catch (Exception e) {
        }
    }

    @Override
    public boolean handleMessage(final Message msg) {
        if (msg.what == LAUNCH_ACTIVITY) {
            // LAUNCH_ACTIVITY
            HookHandle_LaunchActivity.handleLaunchActivity(msg);
        } else if (msg.what == CREATE_SERVICE) {
            // CREATE_SERVICE
            HookHandle_CreateService.handleCreateService(msg);
        } else if (msg.what == SERVICE_ARGS) {
            // SERVICE_ARGS
            HookHandle_ServiceArgs.handleServiceArgs(msg);
        } else if (msg.what == STOP_SERVICE) {
            // STOP_SERVICE
            HookHandle_StopService.handleStopService(msg);
        } else if (msg.what == BIND_SERVICE) {
            // BIND_SERVICE
            HookHandle_BindService.handleBindService(msg);
        } else if (msg.what == UNBIND_SERVICE) {
            // UNBIND_SERVICE
            HookHandle_UnbindService.handleUnbindService(msg);
        }
        mBase.handleMessage(msg);
        return true;
    }
}
