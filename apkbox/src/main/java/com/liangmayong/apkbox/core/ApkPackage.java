package com.liangmayong.apkbox.core;

import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.liangmayong.apkbox.core.classloader.ApkClassLoader;
import com.liangmayong.apkbox.core.context.ApkContext;
import com.liangmayong.apkbox.core.manager.ApkApplicationMgr;
import com.liangmayong.apkbox.core.resources.ApkResources;

/**
 * Created by LiangMaYong on 2017/3/30.
 */
public class ApkPackage {

    private final ApkLoaded loaded;

    public ApkPackage(ApkLoaded loaded) {
        this.loaded = loaded;
    }

    /**
     * getApplicationInfo
     *
     * @return applicationInfo
     */
    public ApplicationInfo getApplicationInfo() {
        if (loaded.getApkInfo() == null)
            return null;
        return loaded.getApkInfo().applicationInfo;
    }

    /**
     * getActivityInfo
     *
     * @param actName actName
     * @return activity info
     */
    public ActivityInfo getActivityInfo(String actName) {
        String activityName = parserPackageClassName(actName);
        if (loaded.getApkInfo().activities != null) {
            for (ActivityInfo act : loaded.getApkInfo().activities) {
                if (act.name.equals(activityName)) {
                    ApplicationInfo info = getApplicationInfo();
                    if (info != null) {
                        act.applicationInfo = info;
                    }
                    return act;
                }
            }
        }
        return null;
    }

    /**
     * parserPackageClassName
     *
     * @param className className
     * @return className
     */
    private String parserPackageClassName(String className) {
        String newClassName = "";
        if (className.startsWith(".")) {
            newClassName = getPackageName() + className;
        } else if (className.indexOf(".") == -1) {
            newClassName = getPackageName() + "." + className;
        } else {
            newClassName = className;
        }
        return newClassName;
    }

    public String getPackageName() {
        if (loaded.getApkInfo() == null)
            return null;
        return loaded.getApkInfo().packageName;
    }

    public ClassLoader getClassLoader() {
        return ApkClassLoader.getClassloader(loaded.getApkPath());
    }

    public Resources getResources(Context context) {
        return ApkResources.getResources(context, loaded.getApkPath());
    }

    public AssetManager getAssets(Context context) {
        return ApkResources.getAssets(context, loaded.getApkPath());
    }

    public Context getContext(Context baseContext) {
        return ApkContext.get(baseContext, loaded.getApkPath());
    }

    public Application getApplication() {
        return ApkApplicationMgr.handleCreateApplication(loaded.getApkPath());
    }

}
