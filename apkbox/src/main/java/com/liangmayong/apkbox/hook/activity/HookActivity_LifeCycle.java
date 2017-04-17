/**
 The MIT License (MIT)

 Copyright (c) 2017 LiangMaYong ( ibeam@qq.com )

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/ or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 **/
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

    public static void onNewIntent(Activity target,Intent intent, boolean after) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_onNewIntent " + (after ? "after" : ""), null);
    }


    public static void OnUserLeaving(Activity target, boolean after) {
        if (DEBUG_LIFECYCLE)
            ApkLogger.get().debug("LifeCycle_OnUserLeaving " + (after ? "after" : ""), null);
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
