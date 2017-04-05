package com.liangmayong.apkbox.hook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.liangmayong.apkbox.core.classloader.ApkClassLoader;
import com.liangmayong.apkbox.core.constant.ApkConstant;

/**
 * Created by LiangMaYong on 2017/4/5.
 */

public class HookActivity_New {

    private HookActivity_New() {
    }

    public static Activity onNewActivity(ClassLoader cl, String className, Intent intent) {
        if (intent.getComponent() != null) {
            if (intent.hasExtra(ApkConstant.EXTRA_APK_PATH)) {
                String apkPath = intent.getStringExtra(ApkConstant.EXTRA_APK_PATH);
                ClassLoader classloader = ApkClassLoader.getClassloader(apkPath);
                try {
                    return (Activity) classloader.loadClass(className).newInstance();
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

}
