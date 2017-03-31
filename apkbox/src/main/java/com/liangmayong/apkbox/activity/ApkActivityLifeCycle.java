package com.liangmayong.apkbox.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.liangmayong.apkbox.core.classloader.ApkClassLoader;

/**
 * Created by LiangMaYong on 2017/3/29.
 */
public class ApkActivityLifeCycle {

    /**
     * onCreate
     *
     * @param target             target
     * @param savedInstanceState savedInstanceState
     */
    public static void onCreate(Activity target, Bundle savedInstanceState) {
        Log.e("TAG", "onCreate");
    }

    /**
     * onStart
     *
     * @param target target
     */
    public static void onStart(Activity target) {
        Log.e("TAG", "onStart");
    }

    /**
     * onRestart
     *
     * @param target target
     */
    public static void onRestart(Activity target) {
        Log.e("TAG", "onRestart");
    }

    /**
     * onDestroy
     *
     * @param target target
     */
    public static void onDestroy(Activity target) {
        Log.e("TAG", "onDestroy");
    }

    /**
     * onPause
     *
     * @param target target
     */
    public static void onPause(Activity target) {
        Log.e("TAG", "onPause");
    }

    /**
     * onStop
     *
     * @param target target
     */
    public static void onStop(Activity target) {
        Log.e("TAG", "onStop");
    }

    /**
     * onResume
     *
     * @param target target
     */
    public static void onResume(Activity target) {
        Log.e("TAG", "onResume");
    }


}
