package com.liangmayong.apkbox.hook.modify;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.reflect.ApkReflect;

import java.lang.reflect.Field;

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
            Resources resources = modifyResources(target, loaded);
            Context context = modifyContext(target, loaded);
            modifyActivityInfo(context, resources, target, loaded);
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

    private static void modifyActivityInfo(Context context, Resources resources, Activity target, ApkLoaded loaded) {
        ActivityInfo activityInfo = loaded.getActivityInfo(target.getClass().getName());
        if (activityInfo != null) {
            applyActivityInfo(target, activityInfo);
            applyTheme(context, target, activityInfo, resources);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Intent intent = target.getIntent();
                if (intent != null && target.isTaskRoot()) {
                    String label = "" + loaded.getApkName();
                    Bitmap icon = null;
                    Drawable drawable = loaded.getApkIcon();
                    if (drawable instanceof BitmapDrawable) {
                        icon = ((BitmapDrawable) drawable).getBitmap();
                    }
                    target.setTaskDescription(new ActivityManager.TaskDescription(label, icon));
                }
            }
            if (target.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                    && activityInfo.screenOrientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
                target.setRequestedOrientation(activityInfo.screenOrientation);
            }
        }
    }

    private static void applyActivityInfo(Activity activity, ActivityInfo activityInfo) {
        Field field_mActivityInfo;
        try {
            field_mActivityInfo = Activity.class.getDeclaredField("mActivityInfo");
            field_mActivityInfo.setAccessible(true);
        } catch (Exception e) {
            return;
        }
        try {
            field_mActivityInfo.set(activity, activityInfo);
        } catch (Exception e) {
        }
    }

    private static void applyTheme(Context context, Activity target, ActivityInfo activityInfo, Resources resources) {
        if (activityInfo != null) {
            int resTheme = activityInfo.getThemeResource();
            ApkReflect.setField(target.getClass(), target, "mTheme", context.getTheme());
            if (resTheme != 0) {
                context.getTheme().applyStyle(resTheme, true);
            }
        } else {
            Resources.Theme mTheme = resources.newTheme();
            mTheme.setTo(target.getBaseContext().getTheme());
            ApkReflect.setField(target.getClass(), target, "mTheme", mTheme);
        }
    }

}
