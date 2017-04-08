package com.liangmayong.apkbox.hook;

import android.app.Application;

import com.liangmayong.apkbox.reflect.ApkReflect;

/**
 * Created by liangmayong on 2017/4/8.
 */
public class HookActivityThread {

    private static Object currentActivityThread = null;

    public static Object getCurrentActivityThread(Application application) {
        if (currentActivityThread == null) {
            Object loadedApk = ApkReflect.getField(Application.class, application, "mLoadedApk");
            currentActivityThread = ApkReflect.getField(loadedApk.getClass(), loadedApk, "mActivityThread");
        }
        return currentActivityThread;
    }
}
