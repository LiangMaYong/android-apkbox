package com.liangmayong.apkbox.hook.modify;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.core.resources.ApkLayoutInflater;
import com.liangmayong.apkbox.reflect.ApkReflect;
import com.liangmayong.apkbox.utils.ApkLogger;

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
            modifyResources(target, loaded);
            modifyContext(target, loaded);
            modifyActivityInfo(target, loaded);
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

    private static void modifyActivityInfo(Activity target, ApkLoaded loaded) {
        ActivityInfo activityInfo = loaded.getActivityInfo(target.getClass().getName());
        if (activityInfo != null) {
            applyActivityInfo(target, activityInfo);
            applyTheme(target, activityInfo);
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
        } else {
            ApkLogger.get().debug("getActivityInfo fail : " + target.getClass().getName(), null);
        }
    }

    private static void applyActivityInfo(Activity activity, ActivityInfo activityInfo) {
        Field field_mActivityInfo;
        try {
            field_mActivityInfo = Activity.class.getDeclaredField("mActivityInfo");
            field_mActivityInfo.setAccessible(true);
        } catch (Exception e) {
            ApkLogger.get().debug("applyActivityInfo fail", e);
            return;
        }
        try {
            field_mActivityInfo.set(activity, activityInfo);
        } catch (Exception e) {
            ApkLogger.get().debug("applyActivityInfo fail", e);
        }
    }

    private static void applyTheme(Activity target, ActivityInfo activityInfo) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            try {
                Class<?> clazz = Class.forName("com.android.internal.R.styleable");
                TypedArray typedArray = target.obtainStyledAttributes((int[]) ApkReflect.getField(clazz, null, "Window"));
                if (typedArray != null) {
                    boolean showWallpaper = typedArray.getBoolean((Integer) ApkReflect.getField(clazz, null, "Window_windowShowWallpaper"),
                            false);
                    if (showWallpaper) {
                        target.getWindow().setBackgroundDrawable(WallpaperManager.getInstance(target).getDrawable());
                    }
                    typedArray.recycle();
                }
            } catch (Throwable e) {
            }
        }
        Window window = target.getWindow();
        LayoutInflater originInflater = target.getLayoutInflater();
        if (!(originInflater instanceof ApkLayoutInflater)) {
            try {
                ApkReflect.setField(window.getClass(), window, "mLayoutInflater",
                        new ApkLayoutInflater(originInflater));
            } catch (Exception e) {
            }
        }

        try {
            int mThemeResource = activityInfo.getThemeResource();
            ApkReflect.setField(ContextThemeWrapper.class, target, "mThemeResource", mThemeResource);
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(target, mThemeResource);
            ApkReflect.setField(target.getClass(), target, "mTheme", contextThemeWrapper.getTheme());
        } catch (Exception e) {
            ApkLogger.get().debug("applyTheme fail", e);
        }
    }

}
