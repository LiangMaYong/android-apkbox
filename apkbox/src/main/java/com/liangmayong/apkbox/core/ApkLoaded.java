/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017 LiangMaYong ( ibeam@qq.com )
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/ or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 **/
package com.liangmayong.apkbox.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.liangmayong.apkbox.core.classloader.ApkClassLoader;
import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.core.context.ApkContext;
import com.liangmayong.apkbox.core.loader.ApkLoader;
import com.liangmayong.apkbox.core.resources.ApkInterface;
import com.liangmayong.apkbox.core.resources.ApkResources;

import java.util.HashMap;
import java.util.List;
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
        if (context == null) {
            return null;
        }
        ApkLoaded loaded = ApkLoader.loadApk(context, apkPath);
        if (loaded != null) {
            APKLOADEDS.put(key, loaded);
        }
        return loaded;
    }

    /////////////////////////////////////////////////////////////////////////////////
    /////////// ApkLoaded
    /////////////////////////////////////////////////////////////////////////////////

    private ApkLoaded() {
    }

    private Context hostContext = null;
    private Context context;
    private String apkPath = "";
    private String apkName = "";
    private String apkLauncher = "";
    private Application apkApplication = null;
    private Drawable apkIcon = null;
    private PackageInfo apkInfo = null;
    private String apkSignture = "";
    private String apkSha1 = "";
    private int apkVersionCode = 1;
    private String apkVersionName = "";
    private PackageManager apkPackageManager = null;
    private List<String> permissions = null;
    private List<ProviderInfo> providers = null;
    private final Map<String, String> configures = new HashMap<>();
    private final Map<String, IntentFilter> filters = new HashMap<>();

    public void setHostContext(Context hostContext) {
        this.hostContext = hostContext;
    }

    public Context getHostContext() {
        return hostContext;
    }

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

    public void setApkApplication(Application application) {
        this.apkApplication = application;
    }

    public Application getApkApplication() {
        return apkApplication;
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

    public String getApkSha1() {
        return apkSha1;
    }

    public void setApkSha1(String apkSha1) {
        this.apkSha1 = apkSha1;
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

    public PackageManager getApkPackageManager() {
        return apkPackageManager;
    }

    public void setApkPackageManager(PackageManager apkPackageManager) {
        this.apkPackageManager = apkPackageManager;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public List<ProviderInfo> getProviders() {
        return providers;
    }

    public void setProviders(List<ProviderInfo> providers) {
        this.providers = providers;
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

    /////////////////////////////////////////////////////////////////////
    //////// Hook Class and Res and Interface
    /////////////////////////////////////////////////////////////////////

    /**
     * getClassLoader
     *
     * @return classloader
     */
    public ClassLoader getClassLoader() {
        return ApkClassLoader.getClassloader(getApkPath());
    }

    /**
     * getResources
     *
     * @param context context
     * @return resources
     */
    public Resources getResources(Context context) {
        return ApkResources.getResources(context, getApkPath());
    }

    /**
     * getAssets
     *
     * @param context context
     * @return asset
     */
    public AssetManager getAssets(Context context) {
        return ApkResources.getAssets(context, getApkPath());
    }

    /**
     * getContext
     *
     * @param baseContext baseContext
     * @return context
     */
    public Context getContext(Context baseContext) {
        if (context == null) {
            context = ApkContext.get(baseContext.getApplicationContext(), this);
        }
        return context;
    }

    /**
     * getInterface
     *
     * @param clazz     clazz
     * @param className className
     * @param <T>       type
     * @return interface
     */
    public <T> T getInterface(Class<T> clazz, String className) {
        if (clazz != null) {
            ApkInterface<T> builder = new ApkInterface<T>(clazz);
            return builder.getInterface(getApkPath(), className);
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
                "hostContext=" + hostContext +
                ", apkPath='" + apkPath + '\'' +
                ", apkName='" + apkName + '\'' +
                ", apkLauncher='" + apkLauncher + '\'' +
                ", apkApplication=" + apkApplication +
                ", apkIcon=" + apkIcon +
                ", apkInfo=" + apkInfo +
                ", apkSignture='" + apkSignture + '\'' +
                ", apkSha1='" + apkSha1 + '\'' +
                ", apkVersionCode=" + apkVersionCode +
                ", apkVersionName='" + apkVersionName + '\'' +
                ", apkPackageManager=" + apkPackageManager +
                ", permissions=" + permissions +
                ", providers=" + providers +
                ", configures=" + configures +
                ", filters=" + filters +
                '}';
    }
}
