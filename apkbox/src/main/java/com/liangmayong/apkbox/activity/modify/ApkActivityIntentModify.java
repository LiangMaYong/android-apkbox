package com.liangmayong.apkbox.activity.modify;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.liangmayong.apkbox.activity.ApkActivityConstant;
import com.liangmayong.apkbox.core.classloader.ApkClassLoader;

/**
 * Created by LiangMaYong on 2017/3/30.
 */
public class ApkActivityIntentModify {

    private ApkActivityIntentModify() {
    }

    /**
     * @param intent
     * @param activityIntent
     * @param componentName
     * @return
     */
    public static Intent modifyIntent(Intent intent, Intent activityIntent, ComponentName componentName) {
        Intent targetIntent = null;
        boolean replaceFlag = intent != null
                && intent.getComponent() != null
                && !intent.hasExtra(ApkActivityConstant.EXTRA_APK_MODIFIERED)
                && (intent.hasExtra(ApkActivityConstant.EXTRA_APK_ACTIVITY) || (activityIntent != null && activityIntent.hasExtra(ApkActivityConstant.EXTRA_APK_ACTIVITY)));
        if (replaceFlag) {
            String path = intent.getStringExtra(ApkActivityConstant.EXTRA_APK_PATH);
            if (path == null || "".equals(path)) {
                if (activityIntent != null && activityIntent.hasExtra(ApkActivityConstant.EXTRA_APK_PATH)) {
                    path = activityIntent.getStringExtra(ApkActivityConstant.EXTRA_APK_PATH);
                }
                if (path == null) {
                    path = "";
                }
            }
            String activity = "";
            if (intent.hasExtra(ApkActivityConstant.EXTRA_APK_ACTIVITY)) {
                activity = intent.getStringExtra(ApkActivityConstant.EXTRA_APK_ACTIVITY);
            } else {
                activity = intent.getComponent().getClassName();
            }
            Intent newIntent = new Intent();
            Bundle extras = intent.getExtras();
            if (extras != null) {
                newIntent.putExtras(extras);
            }
            newIntent.setComponent(componentName);
            newIntent.putExtra(ApkActivityConstant.EXTRA_APK_ACTIVITY, activity);
            newIntent.putExtra(ApkActivityConstant.EXTRA_APK_PATH, path);
            newIntent.putExtra(ApkActivityConstant.EXTRA_APK_MODIFIERED, true);
        } else {
            targetIntent = intent;
        }
        return targetIntent;
    }

    /**
     * modifyNewActivity
     *
     * @param cl        cl
     * @param className className
     * @param intent    intent
     * @return new activity
     */
    public static Activity modifyNewActivity(ClassLoader cl, String className, Intent intent) {
        String activityName = "";
        if (intent != null && intent.hasExtra(ApkActivityConstant.EXTRA_APK_ACTIVITY)) {
            activityName = intent.getStringExtra(ApkActivityConstant.EXTRA_APK_ACTIVITY);
        }
        if (activityName != null && !"".equals(activityName)) {
            ClassLoader classLoader = null;
            String path = intent.getStringExtra(ApkActivityConstant.EXTRA_APK_PATH);
            if (path != null && !"".equals(path)) {
                classLoader = ApkClassLoader.getClassloader(path);
            }
            if (classLoader == null) {
                classLoader = cl;
            }
            try {
                return (Activity) classLoader.loadClass(activityName).newInstance();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static void modifyOnCreate(Activity target, Bundle savedInstanceState) {

    }
}
