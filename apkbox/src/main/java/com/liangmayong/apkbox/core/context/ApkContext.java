package com.liangmayong.apkbox.core.context;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.reflect.ApkMethod;


/**
 * Created by liangmayong on 2016/9/18.
 */
public final class ApkContext extends Application {


    /**
     * get
     *
     * @param baseContext baseContext
     * @param apkPath     apkPath
     * @return context
     */
    public static Context get(Context baseContext, String apkPath) {
        ApkContext context = new ApkContext(baseContext);
        context.apkPath = apkPath;
        return context;
    }

    private String apkPath = "";
    private Resources.Theme mTheme = null;

    private ApkContext(Context base) {
        try {
            ApkMethod method = new ApkMethod(getClass(), this, "attach", Context.class);
            method.invoke(base);
        } catch (Exception e) {
        }
    }

    @Override
    public String getPackageName() {
        if (isApkLoaded()) {
            return getApkLoaded().getPackage().getPackageName();
        }
        return super.getPackageName();
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        if (isApkLoaded()) {
            return getApkLoaded().getPackage().getApplicationInfo();
        }
        return super.getApplicationInfo();
    }

    @Override
    public Context getApplicationContext() {
        if (isApkLoaded()) {
            return super.getApplicationContext();
        }
        return this;
    }

    @Override
    public ClassLoader getClassLoader() {
        if (isApkLoaded()) {
            return getApkLoaded().getPackage().getClassLoader();
        }
        return super.getClassLoader();
    }

    @Override
    public AssetManager getAssets() {
        if (isApkLoaded()) {
            return getApkLoaded().getPackage().getAssets(getBaseContext());
        }
        return super.getAssets();
    }

    @Override
    public Resources getResources() {
        if (isApkLoaded()) {
            return getApkLoaded().getPackage().getResources(getBaseContext());
        }
        return super.getResources();
    }

    @Override
    public Resources.Theme getTheme() {
        if (isApkLoaded()) {
            return super.getTheme();
        }
        if (mTheme == null) {
            mTheme = getResources().newTheme();
            mTheme.setTo(super.getTheme());
        }
        return mTheme;
    }

    public ApkLoaded getApkLoaded() {
        return ApkLoaded.get(getBaseContext(), apkPath);
    }

    public boolean isApkLoaded() {
        if (getApkLoaded() != null) {
            return true;
        }
        return false;
    }
}
