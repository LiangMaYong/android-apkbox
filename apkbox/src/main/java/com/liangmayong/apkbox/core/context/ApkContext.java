package com.liangmayong.apkbox.core.context;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.liangmayong.apkbox.core.classloader.ApkClassLoader;
import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.core.resources.ApkResources;
import com.liangmayong.apkbox.hook.service.HookService_Component;
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
        ApkContextModifier.setOuterContext(baseContext, context);
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
    public Context getApplicationContext() {
        if (isApkLoaded()) {
            return this;
        }
        return super.getApplicationContext();
    }

    @Override
    public ClassLoader getClassLoader() {
        if (isApkLoaded()) {
            return ApkClassLoader.getClassloader(apkPath);
        }
        return super.getClassLoader();
    }

    @Override
    public AssetManager getAssets() {
        if (isApkLoaded()) {
            return ApkResources.getAssets(getBaseContext(), apkPath);
        }
        return super.getAssets();
    }

    @Override
    public Resources getResources() {
        if (isApkLoaded()) {
            return ApkResources.getResources(getBaseContext(), apkPath);
        }
        return super.getResources();
    }

    @Override
    public String getPackageName() {
        return getBaseContext().getPackageName();
    }

    @Override
    public Resources.Theme getTheme() {
        if (isApkLoaded()) {
            if (mTheme == null) {
                mTheme = getResources().newTheme();
                mTheme.setTo(super.getTheme());
            }
            return mTheme;
        }
        return super.getTheme();
    }

    public boolean isApkLoaded() {
        if (apkPath != null && !"".equals(apkPath)) {
            return true;
        }
        return false;
    }

    @Override
    public ComponentName startService(Intent service) {
        if (isApkLoaded()) {
            service.putExtra(ApkConstant.EXTRA_APK_PATH, apkPath);
        }
        service = HookService_Component.modify(service);
        return super.startService(service);
    }

    @Override
    public boolean stopService(Intent name) {
        if (isApkLoaded()) {
            name.putExtra(ApkConstant.EXTRA_APK_PATH, apkPath);
        }
        name = HookService_Component.modify(name);
        return super.stopService(name);
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        if (isApkLoaded()) {
            service.putExtra(ApkConstant.EXTRA_APK_PATH, apkPath);
        }
        service = HookService_Component.modify(service);
        return super.bindService(service, conn, flags);
    }

    @Override
    public void sendBroadcast(Intent intent, String receiverPermission) {
        receiverPermission = getPackageName() + ".permission.APK_RECEIVE";
        super.sendBroadcast(intent, receiverPermission);
    }
}
