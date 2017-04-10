package com.liangmayong.apkbox.hook;

import android.app.Application;

import com.liangmayong.apkbox.reflect.ApkReflect;

import java.lang.reflect.Method;

/**
 * Created by liangmayong on 2017/4/8.
 */
public class HookCurrentActivityThread {

    private static Object currentActivityThread = null;
    private static Class<?> activityThreadClass = null;

    public static Object getCurrentActivityThread(Application application) {
        if (currentActivityThread == null) {
            Object loadedApk = ApkReflect.getField(Application.class, application, "mLoadedApk");
            currentActivityThread = ApkReflect.getField(loadedApk.getClass(), loadedApk, "mActivityThread");
        }
        if (currentActivityThread == null) {
            try {
                Method currentActivityThreadMethod = getActivityThreadClass().getDeclaredMethod("currentActivityThread");
                currentActivityThread = currentActivityThreadMethod.invoke(null);
            } catch (Exception e) {
            }
        }
        if (currentActivityThread == null) {
            try {
                currentActivityThread = ApkReflect.getField(getActivityThreadClass(), null, "sCurrentActivityThread");
            } catch (Exception e) {
            }
        }
        return currentActivityThread;
    }

    public static Class<?> getActivityThreadClass() {
        if (activityThreadClass == null) {
            try {
                activityThreadClass = Class.forName("android.app.ActivityThread");
            } catch (Exception e) {
            }
        }
        return activityThreadClass;
    }
}
