package com.liangmayong.apkbox.hook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.liangmayong.apkbox.ApkBox;
import com.liangmayong.apkbox.utils.ApkLogger;

/**
 * Created by LiangMaYong on 2017/4/5.
 */

public class HookActivity_LifeCycle {

    private HookActivity_LifeCycle() {
    }

    public static Activity onNewActivity(ClassLoader cl, String className, Intent intent) {
        ApkLogger.get().debug("LifeCycle_onNewActivity", null);
        return HookActivity_New.onNewActivity(cl, className, intent);
    }

    public static void onPostCreate(Activity target, Bundle icicle) {
        ApkLogger.get().debug("LifeCycle_onPostCreate", null);
        if (ApkBox.get().getActivityListener() == null)
            return;
        ApkBox.get().getActivityListener().onPostCreate(target, icicle);
    }

    public static void onCreate(Activity target, Bundle icicle) {
        ApkLogger.get().debug("LifeCycle_onCreate", null);
        HookActivity_OnCreate.onCreate(target, icicle);
        if (ApkBox.get().getActivityListener() == null)
            return;
        ApkBox.get().getActivityListener().onCreate(target, icicle);
    }

    public static void onStart(Activity target) {
        ApkLogger.get().debug("LifeCycle_onStart", null);
        if (ApkBox.get().getActivityListener() == null)
            return;
        ApkBox.get().getActivityListener().onStart(target);
    }

    public static void onRestart(Activity target) {
        ApkLogger.get().debug("LifeCycle_onRestart", null);
        if (ApkBox.get().getActivityListener() == null)
            return;
        ApkBox.get().getActivityListener().onRestart(target);
    }

    public static void onDestroy(Activity target) {
        ApkLogger.get().debug("LifeCycle_onDestroy", null);
        if (ApkBox.get().getActivityListener() == null)
            return;
        ApkBox.get().getActivityListener().onDestroy(target);
    }

    public static void onPause(Activity target) {
        ApkLogger.get().debug("LifeCycle_onPause", null);
        if (ApkBox.get().getActivityListener() == null)
            return;
        ApkBox.get().getActivityListener().onPause(target);
    }

    public static void onStop(Activity target) {
        ApkLogger.get().debug("LifeCycle_onStop", null);
        if (ApkBox.get().getActivityListener() == null)
            return;
        ApkBox.get().getActivityListener().onStop(target);
    }

    public static void onResume(Activity target) {
        ApkLogger.get().debug("LifeCycle_onResume", null);
        if (ApkBox.get().getActivityListener() == null)
            return;
        ApkBox.get().getActivityListener().onResume(target);
    }

    /////////////////////////////////////////////////////////////////////////////
    //////// Instance State
    /////////////////////////////////////////////////////////////////////////////

    public static void onRestoreInstanceState(Activity activity, Bundle outState) {
        ApkLogger.get().debug("LifeCycle_onRestoreInstanceState", null);
    }

    public static void onSaveInstanceState(Activity activity, Bundle outState) {
        ApkLogger.get().debug("LifeCycle_onSaveInstanceState", null);
    }

    /////////////////////////////////////////////////////////////////////////////
    //////// Persistable
    /////////////////////////////////////////////////////////////////////////////

    public static void onPostCreate(Activity target, Bundle icicle, PersistableBundle persistentState) {
        ApkLogger.get().debug("LifeCycle_onPostCreate", null);
    }

    public static void onCreate(Activity target, Bundle icicle, PersistableBundle persistentState) {
        ApkLogger.get().debug("LifeCycle_onCreate", null);
    }

    public static void onRestoreInstanceState(Activity activity, Bundle outState, PersistableBundle persistentState) {
        ApkLogger.get().debug("LifeCycle_onRestoreInstanceState", null);
    }

    public static void onSaveInstanceState(Activity activity, Bundle outState, PersistableBundle persistentState) {
        ApkLogger.get().debug("LifeCycle_onSaveInstanceState", null);
    }
}
