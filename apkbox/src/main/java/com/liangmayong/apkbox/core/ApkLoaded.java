package com.liangmayong.apkbox.core;

import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;

import com.liangmayong.apkbox.core.loader.ApkLoader;

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
    private String apkMain = "";
    private String apkApp = "";
    private Drawable apkIcon = null;
    private PackageInfo apkInfo = null;
    private String apkSignture = "";
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

    public String getApkMain() {
        return apkMain;
    }

    public void setApkMain(String apkMain) {
        this.apkMain = apkMain;
    }

    public String getApkApp() {
        return apkApp;
    }

    public void setApkApp(String apkApp) {
        this.apkApp = apkApp;
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

    public ApkPackage getPackage() {
        return new ApkPackage(this);
    }

    @Override
    public String toString() {
        return "ApkLoaded{" +
                "apkPath='" + apkPath + '\'' +
                ", apkName='" + apkName + '\'' +
                ", apkMain='" + apkMain + '\'' +
                ", apkApp='" + apkApp + '\'' +
                ", apkIcon=" + apkIcon +
                ", apkInfo=" + apkInfo +
                ", apkSignture='" + apkSignture + '\'' +
                ", configures=" + configures +
                ", filters=" + filters +
                '}';
    }
}
