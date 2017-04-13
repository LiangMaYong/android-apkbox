package com.liangmayong.apkbox.hook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.liangmayong.apkbox.ApkBox;
import com.liangmayong.apkbox.utils.ApkBuild;
import com.liangmayong.apkbox.utils.ApkLogger;

/**
 * Created by LiangMaYong on 2017/4/5.
 */

public class HookActivity_LifeCycle {

    private static final boolean DEBUG_LIFECYCLE = ApkBuild.DEBUG_LIFECYCLE;

    private HookActivity_LifeCycle() {
    }

    public static Activity onNewActivity(ClassLoader cl, String className, Intent intent) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_onNewActivity", null);
        return HookActivity_New.onNewActivity(cl, className, intent);
    }

    public static void onPostCreate(Activity target, Bundle icicle, boolean after) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_onPostCreate " + (after ? "after" : ""), null);
        if (ApkBox.get().getActivityListener() == null)
            return;
        ApkBox.get().getActivityListener().onPostCreate(target, icicle, after);
    }

    public static void onCreate(Activity target, Bundle icicle, boolean after) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_onCreate " + (after ? "after" : ""), null);
        HookActivity_OnCreate.onCreate(target, icicle, after);
        if (ApkBox.get().getActivityListener() == null)
            return;
        ApkBox.get().getActivityListener().onCreate(target, icicle, after);
    }

    public static void onStart(Activity target, boolean after) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_onStart " + (after ? "after" : ""), null);
        if (ApkBox.get().getActivityListener() == null)
            return;
        ApkBox.get().getActivityListener().onStart(target, after);
    }

    public static void onRestart(Activity target, boolean after) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_onRestart " + (after ? "after" : ""), null);
        if (ApkBox.get().getActivityListener() == null)
            return;
        ApkBox.get().getActivityListener().onRestart(target, after);
    }

    public static void onDestroy(Activity target, boolean after) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_onDestroy " + (after ? "after" : ""), null);
        if (ApkBox.get().getActivityListener() == null)
            return;
        ApkBox.get().getActivityListener().onDestroy(target, after);
    }

    public static void onPause(Activity target, boolean after) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_onPause " + (after ? "after" : ""), null);
        if (ApkBox.get().getActivityListener() == null)
            return;
        ApkBox.get().getActivityListener().onPause(target, after);
    }

    public static void onStop(Activity target, boolean after) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_onStop " + (after ? "after" : ""), null);
        if (ApkBox.get().getActivityListener() == null)
            return;
        ApkBox.get().getActivityListener().onStop(target, after);
    }

    public static void onResume(Activity target, boolean after) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_onResume " + (after ? "after" : ""), null);
        if (ApkBox.get().getActivityListener() == null)
            return;
        ApkBox.get().getActivityListener().onResume(target, after);
    }

    /////////////////////////////////////////////////////////////////////////////
    //////// Instance State
    /////////////////////////////////////////////////////////////////////////////

    public static void onRestoreInstanceState(Activity activity, Bundle outState, boolean after) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_onRestoreInstanceState " + (after ? "after" : ""), null);
    }

    public static void onSaveInstanceState(Activity activity, Bundle outState, boolean after) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_onSaveInstanceState " + (after ? "after" : ""), null);
    }

    /////////////////////////////////////////////////////////////////////////////
    //////// Persistable
    /////////////////////////////////////////////////////////////////////////////

    public static void onPostCreate(Activity target, Bundle icicle, PersistableBundle persistentState, boolean after) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_onPostCreate " + (after ? "after" : ""), null);
    }

    public static void onCreate(Activity target, Bundle icicle, PersistableBundle persistentState, boolean after) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_onCreate " + (after ? "after" : ""), null);
    }

    public static void onRestoreInstanceState(Activity activity, Bundle outState, PersistableBundle persistentState, boolean after) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_onRestoreInstanceState " + (after ? "after" : ""), null);
    }

    public static void onSaveInstanceState(Activity activity, Bundle outState, PersistableBundle persistentState, boolean after) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_onSaveInstanceState " + (after ? "after" : ""), null);
    }
}
