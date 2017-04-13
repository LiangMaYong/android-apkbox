package com.liangmayong.apkbox.hook.modify;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.reflect.ApkReflect;

/**
 * Created by LiangMaYong on 2016/9/21.
 */
public class ApkActivityModifier {

    private ApkActivityModifier() {
    }

    public static void modify(Activity target, String apkPath) {
        ApkLoaded loaded = ApkLoaded.get(target, apkPath);
        if (loaded != null) {
            modifyApplication(target, loaded);
            modifyTitle(target, loaded);
            modifyResources(target, loaded);
            modifyContext(target, loaded);
        }
    }

    private static void modifyApplication(Activity target, ApkLoaded loaded) {
        Application application = loaded.getApkApplication();
        if (application != null) {
            ApkReflect.setField(target.getClass(), target, "mApplication", application);
        }
    }

    private static Resources modifyResources(Activity target, ApkLoaded loaded) {
        Resources resources = loaded.getResources(target);
        if (resources != null) {
            ApkReflect.setField(target.getClass(), target, "mResources", resources);
        }
        return resources;
    }

    private static void modifyTitle(Activity target, ApkLoaded loaded) {
        try {
            target.setTitle(loaded.getApkName());
        } catch (Exception e) {
        }
    }

    private static Context modifyContext(Activity target, ApkLoaded loaded) {
        Context context = loaded.getContext(target.getBaseContext());
        if (context != null) {
            ApkReflect.setField(target.getClass(), target, "mBase", context);
        }
        return context;
    }

}
