package com.liangmayong.apkbox.activity;

import android.app.Application;
import android.app.Instrumentation;

import com.liangmayong.apkbox.activity.core.ApkInstrumentation;
import com.liangmayong.apkbox.reflect.ApkReflect;

/**
 * Created by LiangMaYong on 2017/3/29.
 */
public class ApkActivityHook {

    private static volatile ApkActivityHook ourInstance = null;

    public static ApkActivityHook getInstance() {
        if (ourInstance == null) {
            synchronized (ApkActivityHook.class) {
                ourInstance = new ApkActivityHook();
            }
        }
        return ourInstance;
    }

    private ApkActivityHook() {
    }

    /**
     * hook
     *
     * @param application application
     * @return true or false
     */
    public boolean hook(Application application) {
        if (application == null) {
            return false;
        }
        try {
            Object loadedApk = ApkReflect.getField(Application.class, application, "mLoadedApk");
            if (loadedApk != null) {
                Object activityThread = ApkReflect.getField(loadedApk.getClass(), loadedApk, "mActivityThread");
                if (activityThread != null) {
                    Instrumentation rawInstrumentation = (Instrumentation) ApkReflect.getField(activityThread.getClass(),
                            activityThread, "mInstrumentation");
                    ApkInstrumentation instrumentation = new ApkInstrumentation(rawInstrumentation);
                    ApkReflect.setField(activityThread.getClass(), activityThread, "mInstrumentation", instrumentation);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
