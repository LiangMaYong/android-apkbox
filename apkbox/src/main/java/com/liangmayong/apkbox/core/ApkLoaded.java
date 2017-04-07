package com.liangmayong.apkbox.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.liangmayong.apkbox.core.classloader.ApkClassLoader;
import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.core.context.ApkContext;
import com.liangmayong.apkbox.core.loader.ApkLoader;
import com.liangmayong.apkbox.core.resources.ApkModel;
import com.liangmayong.apkbox.core.resources.ApkResources;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/3/30.
 */
public class ApkLoaded {

    // APKLOADEDS
    private static final Map<String, ApkLoaded> APKLOADEDS = new HashMap<String, ApkLoaded>();

    /**
     * get
     *
     * @param context context
     * @param apkPath apkPath
     * @return apk loaded
     */
    public static ApkLoaded get(Context context, String apkPath) {
        if (apkPath == null || "".equals(apkPath)) {
            return null;
        }
        String key = apkPath;
        if (APKLOADEDS.containsKey(key)) {
            return APKLOADEDS.get(key);
        }
        ApkLoaded info = ApkLoader.loadApk(context, apkPath);
        if (info != null) {
            APKLOADEDS.put(key, info);
        }
        return info;
    }

    /////////////////////////////////////////////////////////////////////////////////
    /////////// ApkLoaded
    /////////////////////////////////////////////////////////////////////////////////

    private ApkLoaded() {
    }

    private String apkPath = "";
    private String apkName = "";
    private String apkLauncher = "";
    private Application apkApplication = null;
    private Drawable apkIcon = null;
    private PackageInfo apkInfo = null;
    private String apkSignture = "";
    private int apkVersionCode = 1;
    private String apkVersionName = "";
    private final Map<String, String> configures = new HashMap<>();
    private final Map<String, IntentFilter> filters = new HashMap<>();

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getApkLauncher() {
        return apkLauncher;
    }

    public void setApkLauncher(String apkLauncher) {
        this.apkLauncher = apkLauncher;
    }

    public Application getApkApplication() {
        return apkApplication;
    }

    public void setApkApplication(Application apkApplication) {
        this.apkApplication = apkApplication;
    }

    public Drawable getApkIcon() {
        return apkIcon;
    }

    public void setApkIcon(Drawable apkIcon) {
        this.apkIcon = apkIcon;
    }

    public PackageInfo getApkInfo() {
        return apkInfo;
    }

    public void setApkInfo(PackageInfo apkInfo) {
        this.apkInfo = apkInfo;
    }

    public String getApkSignture() {
        return apkSignture;
    }

    public void setApkSignture(String apkSignture) {
        this.apkSignture = apkSignture;
    }

    public int getApkVersionCode() {
        return apkVersionCode;
    }

    public void setApkVersionCode(int apkVersionCode) {
        this.apkVersionCode = apkVersionCode;
    }

    public String getApkVersionName() {
        return apkVersionName;
    }

    public void setApkVersionName(String apkVersionName) {
        this.apkVersionName = apkVersionName;
    }

    public Map<String, String> getConfigures() {
        return configures;
    }

    public void setConfigures(Map<String, String> configures) {
        this.configures.clear();
        if (configures != null) {
            this.configures.putAll(configures);
        }
    }

    public String getConfigure(String key) {
        String configureValue = "";
        if (configures != null) {
            if (configures.containsKey(key)) {
                configureValue = configures.get(key);
            }
        }
        return configureValue;
    }

    public void setConfigure(String key, String configureValue) {
        if (configures != null) {
            configures.put(key, configureValue);
        }
    }

    public Map<String, IntentFilter> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, IntentFilter> filters) {
        this.filters.clear();
        if (filters != null) {
            this.filters.putAll(filters);
        }
    }

    public IntentFilter getFilter(String key) {
        IntentFilter intentFilter = null;
        if (filters != null) {
            if (filters.containsKey(key)) {
                intentFilter = filters.get(key);
            }
        }
        return intentFilter;
    }

    public void setFilter(String key, IntentFilter intentFilter) {
        if (filters != null) {
            filters.put(key, intentFilter);
        }
    }

    /////////////////////////////////////////////////////////////////////
    //////// Hook Class and Res and Model
    /////////////////////////////////////////////////////////////////////

    public ClassLoader getClassLoader() {
        return ApkClassLoader.getClassloader(getApkPath());
    }

    public Resources getResources(Context context) {
        return ApkResources.getResources(context, getApkPath());
    }

    public AssetManager getAssets(Context context) {
        return ApkResources.getAssets(context, getApkPath());
    }

    public Context getContext(Context baseContext) {
        return ApkContext.get(baseContext, getApkPath());
    }

    public <T> T getModel(Class<T> clazz, String className) {
        if (clazz != null) {
            ApkModel<T> builder = new ApkModel<T>(clazz);
            return builder.getModel(getApkPath(), className);
        }
        return null;
    }


    /**
     * getActivityInfo
     *
     * @param actName actName
     * @return activity info
     */
    public ActivityInfo getActivityInfo(String actName) {
        String activityName = ApkLoader.parserClassName(getApkInfo().packageName, actName);
        if (getApkInfo().activities != null) {
            for (ActivityInfo act : getApkInfo().activities) {
                if (act.name.equals(activityName)) {
                    ApplicationInfo info = getApkInfo().applicationInfo;
                    if (info != null) {
                        act.applicationInfo = info;
                    }
                    return act;
                }
            }
        }
        return null;
    }

    /////////////////////////////////////////////////////////////////////
    //////// Launch
    /////////////////////////////////////////////////////////////////////

    public boolean launch(Context context, Bundle bundle) {
        try {
            Class<?> actClass = getClassLoader().loadClass(getApkLauncher());
            Intent intent = new Intent(context, actClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            intent.putExtra(ApkConstant.EXTRA_APK_PATH, getApkPath());
            Log.e("TAG-LAUNCH", intent + "");
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean launchForResult(Activity activity, Bundle bundle, int requestCode) {
        try {
            Class<?> actClass = getClassLoader().loadClass(getApkLauncher());
            Intent intent = new Intent(activity, actClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            intent.putExtra(ApkConstant.EXTRA_APK_PATH, getApkPath());
            activity.startActivityForResult(intent, requestCode);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public String toString() {
        return "ApkLoaded{" +
                "apkPath='" + apkPath + '\'' +
                ", apkName='" + apkName + '\'' +
                ", apkLauncher='" + apkLauncher + '\'' +
                ", apkApplication=" + apkApplication +
                ", apkIcon=" + apkIcon +
                ", apkInfo=" + apkInfo +
                ", apkSignture='" + apkSignture + '\'' +
                ", apkVersionCode=" + apkVersionCode +
                ", apkVersionName='" + apkVersionName + '\'' +
                ", configures=" + configures +
                ", filters=" + filters +
                '}';
    }
}
