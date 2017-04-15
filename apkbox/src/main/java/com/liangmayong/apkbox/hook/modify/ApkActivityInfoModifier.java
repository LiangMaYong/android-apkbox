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
package com.liangmayong.apkbox.hook.modify;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Window;

import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.core.resources.ApkLayoutInflater;
import com.liangmayong.apkbox.reflect.ApkReflect;
import com.liangmayong.apkbox.utils.ApkLogger;

/**
 * Created by LiangMaYong on 2016/9/21.
 */
public class ApkActivityInfoModifier {

    private ApkActivityInfoModifier() {
    }

    public static void modify(Activity target, String apkPath) {
        ApkLoaded loaded = ApkLoaded.get(target, apkPath);
        if (loaded != null) {
            modifyActivityInfo(target, loaded);
        }
    }

    private static void modifyActivityInfo(Activity target, ApkLoaded loaded) {
        ActivityInfo activityInfo = loaded.getActivityInfo(target.getClass().getName());
        if (activityInfo != null) {
            applyActivityInfo(target, activityInfo);
            applyTheme(target, activityInfo);
            applyRequestedOrientation(target, activityInfo);
            applyTaskDescription(target, loaded);
        } else {
            ApkLogger.get().debug("getActivityInfo fail : " + target.getClass().getName(), null);
        }
    }

    private static void applyTaskDescription(Activity target, ApkLoaded loaded) {
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
    }

    private static void applyRequestedOrientation(Activity target, ActivityInfo activityInfo) {
        if (target.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                && activityInfo.screenOrientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            target.setRequestedOrientation(activityInfo.screenOrientation);
        }
    }

    private static void applyActivityInfo(Activity activity, ActivityInfo activityInfo) {
        ApkReflect.setField(Activity.class, activity, "mActivityInfo", activityInfo);
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
                ApkReflect.setField(window.getClass(), window, "mLayoutInflater", new ApkLayoutInflater(originInflater));
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
