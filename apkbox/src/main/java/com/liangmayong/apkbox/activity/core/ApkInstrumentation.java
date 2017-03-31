package com.liangmayong.apkbox.activity.core;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.Instrumentation;
import android.app.UiAutomation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.liangmayong.apkbox.activity.ApkActivityLifeCycle;
import com.liangmayong.apkbox.activity.modify.ApkActivityIntentModify;
import com.liangmayong.apkbox.activity.proxy.ProxyActivity;
import com.liangmayong.apkbox.reflect.ApkMethod;

/**
 * Created by liangmayong on 2016/9/18.
 */
public final class ApkInstrumentation extends Instrumentation {

    private Instrumentation mInstrumentation;

    public ApkInstrumentation(Instrumentation mInstrumentation) {
        this.mInstrumentation = mInstrumentation;
    }

    @Override
    public void onCreate(Bundle arguments) {
        mInstrumentation.onCreate(arguments);
    }

    @Override
    public void start() {
        mInstrumentation.start();
    }

    @Override
    public void onStart() {
        mInstrumentation.onStart();
    }

    @Override
    public boolean onException(Object obj, Throwable e) {
        return mInstrumentation.onException(obj, e);
    }

    @Override
    public void sendStatus(int resultCode, Bundle results) {
        mInstrumentation.sendStatus(resultCode, results);
    }

    @Override
    public void finish(int resultCode, Bundle results) {
        mInstrumentation.finish(resultCode, results);
    }

    @Override
    public void setAutomaticPerformanceSnapshots() {
        mInstrumentation.setAutomaticPerformanceSnapshots();
    }

    @Override
    public void startPerformanceSnapshot() {
        mInstrumentation.startPerformanceSnapshot();
    }

    @Override
    public void endPerformanceSnapshot() {
        mInstrumentation.endPerformanceSnapshot();
    }

    @Override
    public void onDestroy() {
        mInstrumentation.onDestroy();
    }

    @Override
    public Context getContext() {
        return mInstrumentation.getContext();
    }

    @Override
    public ComponentName getComponentName() {
        return mInstrumentation.getComponentName();
    }

    @Override
    public Context getTargetContext() {
        return mInstrumentation.getTargetContext();
    }

    @Override
    public boolean isProfiling() {
        return mInstrumentation.isProfiling();
    }

    @Override
    public void startProfiling() {
        mInstrumentation.startProfiling();
    }

    @Override
    public void stopProfiling() {
        mInstrumentation.stopProfiling();
    }

    @Override
    public void setInTouchMode(boolean inTouch) {
        mInstrumentation.setInTouchMode(inTouch);
    }

    @Override
    public void waitForIdle(Runnable recipient) {
        mInstrumentation.waitForIdle(recipient);
    }

    @Override
    public void waitForIdleSync() {
        mInstrumentation.waitForIdleSync();
    }

    @Override
    public void runOnMainSync(Runnable runner) {
        mInstrumentation.runOnMainSync(runner);
    }

    @Override
    public Activity startActivitySync(Intent intent) {
        return mInstrumentation.startActivitySync(intent);
    }

    @Override
    public void addMonitor(ActivityMonitor monitor) {
        mInstrumentation.addMonitor(monitor);
    }

    @Override
    public ActivityMonitor addMonitor(IntentFilter filter, ActivityResult result, boolean block) {
        return mInstrumentation.addMonitor(filter, result, block);
    }

    @Override
    public ActivityMonitor addMonitor(String cls, ActivityResult result, boolean block) {
        return mInstrumentation.addMonitor(cls, result, block);
    }

    @Override
    public boolean checkMonitorHit(ActivityMonitor monitor, int minHits) {
        return mInstrumentation.checkMonitorHit(monitor, minHits);
    }

    @Override
    public Activity waitForMonitor(ActivityMonitor monitor) {
        return mInstrumentation.waitForMonitor(monitor);
    }

    @Override
    public Activity waitForMonitorWithTimeout(ActivityMonitor monitor, long timeOut) {
        return mInstrumentation.waitForMonitorWithTimeout(monitor, timeOut);
    }

    @Override
    public void removeMonitor(ActivityMonitor monitor) {
        mInstrumentation.removeMonitor(monitor);
    }

    @Override
    public boolean invokeMenuActionSync(Activity targetActivity, int requestCode, int flag) {
        return mInstrumentation.invokeMenuActionSync(targetActivity, requestCode, flag);
    }

    @Override
    public boolean invokeContextMenuAction(Activity targetActivity, int requestCode, int flag) {
        return mInstrumentation.invokeContextMenuAction(targetActivity, requestCode, flag);
    }

    @Override
    public void sendStringSync(String text) {
        mInstrumentation.sendStringSync(text);
    }

    @Override
    public void sendKeySync(KeyEvent event) {
        mInstrumentation.sendKeySync(event);
    }

    @Override
    public void sendKeyDownUpSync(int key) {
        mInstrumentation.sendKeyDownUpSync(key);
    }

    @Override
    public void sendCharacterSync(int keyCode) {
        mInstrumentation.sendCharacterSync(keyCode);
    }

    @Override
    public void sendPointerSync(MotionEvent event) {
        mInstrumentation.sendPointerSync(event);
    }

    @Override
    public void sendTrackballEventSync(MotionEvent event) {
        mInstrumentation.sendTrackballEventSync(event);
    }

    @Override
    public Application newApplication(ClassLoader cl, String className, Context who)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return mInstrumentation.newApplication(cl, className, who);
    }

    @Override
    public void callApplicationOnCreate(Application app) {
        mInstrumentation.callApplicationOnCreate(app);
    }

    @Override
    public Activity newActivity(Class<?> clazz, Context who, IBinder token, Application application, Intent intent,
                                ActivityInfo info, CharSequence title, Activity parent, String id, Object lastNonConfigurationInstance)
            throws InstantiationException, IllegalAccessException {
        return mInstrumentation.newActivity(clazz, who, token, application, intent, info, title, parent, id,
                lastNonConfigurationInstance);
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Activity activity = ApkActivityIntentModify.modifyNewActivity(cl, className, intent);
        if (activity != null) {
            return activity;
        }
        return mInstrumentation.newActivity(cl, className, intent);
    }

    @Override
    public void callActivityOnCreate(Activity target, Bundle icicle) {
        ApkActivityIntentModify.modifyOnCreate(target, icicle);
        ApkActivityLifeCycle.onCreate(target, icicle);
        mInstrumentation.callActivityOnCreate(target, icicle);
    }

    @Override
    public void callActivityOnDestroy(Activity target) {
        ApkActivityLifeCycle.onDestroy(target);
        mInstrumentation.callActivityOnDestroy(target);
    }

    @Override
    public void callActivityOnRestoreInstanceState(Activity target, Bundle savedInstanceState) {
        mInstrumentation.callActivityOnRestoreInstanceState(target, savedInstanceState);
    }

    @Override
    public void callActivityOnPostCreate(Activity target, Bundle icicle) {
        mInstrumentation.callActivityOnPostCreate(target, icicle);
    }

    @Override
    public void callActivityOnNewIntent(Activity target, Intent intent) {
        mInstrumentation.callActivityOnNewIntent(target, intent);
    }

    @Override
    public void callActivityOnStart(Activity target) {
        ApkActivityLifeCycle.onStart(target);
        mInstrumentation.callActivityOnStart(target);
    }

    @Override
    public void callActivityOnRestart(Activity target) {
        ApkActivityLifeCycle.onRestart(target);
        mInstrumentation.callActivityOnRestart(target);
    }

    @Override
    public void callActivityOnResume(Activity target) {
        ApkActivityLifeCycle.onResume(target);
        mInstrumentation.callActivityOnResume(target);
    }

    @Override
    public void callActivityOnStop(Activity target) {
        ApkActivityLifeCycle.onStop(target);
        mInstrumentation.callActivityOnStop(target);
    }

    @Override
    public void callActivityOnSaveInstanceState(Activity target, Bundle outState) {
        mInstrumentation.callActivityOnSaveInstanceState(target, outState);
    }

    @Override
    public void callActivityOnPause(Activity target) {
        ApkActivityLifeCycle.onPause(target);
        mInstrumentation.callActivityOnPause(target);
    }

    @Override
    public void callActivityOnUserLeaving(Activity target) {
        mInstrumentation.callActivityOnUserLeaving(target);
    }

    @Override
    @Deprecated
    public void startAllocCounting() {
        mInstrumentation.startAllocCounting();
    }

    @Override
    @Deprecated
    public void stopAllocCounting() {
        mInstrumentation.stopAllocCounting();
    }

    @Override
    public Bundle getAllocCounts() {
        return mInstrumentation.getAllocCounts();
    }

    @Override
    public Bundle getBinderCounts() {
        return mInstrumentation.getBinderCounts();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public UiAutomation getUiAutomation() {
        return mInstrumentation.getUiAutomation();
    }


    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target,
                                            Intent intent, int requestCode) throws Exception {
        return execStartActivity(who, contextThread, token, target, intent, requestCode, null);
    }


    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target,
                                            Intent intent, int requestCode, Bundle options) throws Exception {
        try {
            Intent targetIntent = ApkActivityIntentModify.modifyIntent(intent, target.getIntent(), new ComponentName(who.getPackageName(), ProxyActivity.class.getName()));
            if (targetIntent == null) {
                targetIntent = intent;
            }
            return proxyExecStartActivity(who, contextThread, token, target, targetIntent, requestCode, options);
        } catch (Exception error) {
            throw error;
        }
    }

    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Fragment fragment,
                                            Intent intent, int requestCode) throws Exception {
        return execStartActivity(who, contextThread, token, fragment, intent, requestCode, null);
    }

    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Fragment fragment,
                                            Intent intent, int requestCode, Bundle options) throws Exception {
        try {
            Intent targetIntent = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                targetIntent = ApkActivityIntentModify.modifyIntent(intent, fragment.getActivity().getIntent(), new ComponentName(who.getPackageName(), ProxyActivity.class.getName()));
            } else {
                targetIntent = intent;
            }
            ApkMethod method = new ApkMethod(Instrumentation.class, mInstrumentation, "execStartActivity", Context.class, IBinder.class, IBinder.class, Fragment.class, Intent.class, int.class, Bundle.class);
            return method.invoke(who, contextThread, token, fragment, targetIntent, requestCode, options);
        } catch (Exception error) {
            throw error;
        }
    }

    protected ActivityResult proxyExecStartActivity(Context who, IBinder contextThread, IBinder token, Activity target,
                                                    Intent intent, int requestCode, Bundle options) throws Exception {
        try {
            ApkMethod method = new ApkMethod(Instrumentation.class, mInstrumentation, "execStartActivity", Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class);
            return method.invoke(who, contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            throw e;
        }
    }
}
