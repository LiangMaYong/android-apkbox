package com.liangmayong.apkbox.hook.method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.liangmayong.apkbox.hook.activity.HookActivity_New;
import com.liangmayong.apkbox.hook.activity.HookActivity_OnCreate;

/**
 * Created by LiangMaYong on 2017/4/5.
 */

public class HookActivityLifeCycle {

    private HookActivityLifeCycle() {
    }

    public static Activity onNewActivity(ClassLoader cl, String className, Intent intent) {
        return HookActivity_New.onNewActivity(cl, className, intent);
    }

    public static void onPostCreate(Activity target, Bundle icicle) {
        Log.e("TAG", "onPostCreate");
    }

    public static void onCreate(Activity target, Bundle icicle) {
        HookActivity_OnCreate.onCreate(target, icicle);
        Log.e("TAG", "onCreate");
        Log.e("TAG", "onCreate:" + target.getApplication());
    }

    public static void onStart(Activity target) {
        Log.e("TAG", "onStart");
    }

    public static void onRestart(Activity target) {
        Log.e("TAG", "onRestart");
    }

    public static void onDestroy(Activity target) {
        Log.e("TAG", "onDestroy");
    }

    public static void onPause(Activity target) {
        Log.e("TAG", "onPause");
    }

    public static void onStop(Activity target) {
        Log.e("TAG", "onStop");
    }

    public static void onResume(Activity target) {
        Log.e("TAG", "onResume");
    }

    /////////////////////////////////////////////////////////////////////////////
    //////// Instance State
    /////////////////////////////////////////////////////////////////////////////

    public static void onRestoreInstanceState(Activity activity, Bundle outState) {
        Log.e("TAG", "onRestoreInstanceState");
    }

    public static void onSaveInstanceState(Activity activity, Bundle outState) {
        Log.e("TAG", "onSaveInstanceState");
    }

    /////////////////////////////////////////////////////////////////////////////
    //////// Persistable
    /////////////////////////////////////////////////////////////////////////////

    public static void onPostCreate(Activity target, Bundle icicle, PersistableBundle persistentState) {
        Log.e("TAG", "onPostCreate");
    }

    public static void onCreate(Activity target, Bundle icicle, PersistableBundle persistentState) {
        Log.e("TAG", "onCreate");
    }

    public static void onRestoreInstanceState(Activity activity, Bundle outState, PersistableBundle persistentState) {
        Log.e("TAG", "onRestoreInstanceState");
    }

    public static void onSaveInstanceState(Activity activity, Bundle outState, PersistableBundle persistentState) {
        Log.e("TAG", "onSaveInstanceState");
    }
}
