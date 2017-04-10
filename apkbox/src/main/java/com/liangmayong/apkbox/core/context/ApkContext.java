package com.liangmayong.apkbox.core.context;

import android.app.Application;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.core.resources.ApkLayoutInflater;
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
     * @param loaded      loaded
     * @return context
     */
    public static Context get(Context baseContext, ApkLoaded loaded) {
        ApkContext context = new ApkContext(baseContext);
        context.loaded = loaded;
        ApkContextModifier.setOuterContext(baseContext, context);
        return context;
    }

    /**
     * get
     *
     * @param baseContext baseContext
     * @param apkPath     apkPath
     * @return context
     */
    public static Context get(Context baseContext, String apkPath) {
        ApkContext context = new ApkContext(baseContext);
        context.loaded = ApkLoaded.get(baseContext, apkPath);
        ApkContextModifier.setOuterContext(baseContext, context);
        return context;
    }

    private ApkLoaded loaded = null;
    private Resources.Theme mTheme = null;
    private ContentResolver contentResolver = null;

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
            return loaded.getClassLoader();
        }
        return super.getClassLoader();
    }

    @Override
    public AssetManager getAssets() {
        if (isApkLoaded()) {
            return loaded.getAssets(getBaseContext());
        }
        return super.getAssets();
    }

    @Override
    public Resources getResources() {
        if (isApkLoaded()) {
            return loaded.getResources(getBaseContext());
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
                mTheme.setTo(getBaseContext().getTheme());
            }
            return mTheme;
        }
        return super.getTheme();
    }

    public boolean isApkLoaded() {
        if (loaded != null) {
            return true;
        }
        return false;
    }

    @Override
    public ComponentName startService(Intent service) {
        if (isApkLoaded()) {
            service.putExtra(ApkConstant.EXTRA_APK_PATH, loaded.getApkPath());
        }
        service = HookService_Component.modify(service);
        return super.startService(service);
    }


    @Override
    public void startActivity(Intent intent) {
        if (isApkLoaded()) {
            intent.putExtra(ApkConstant.EXTRA_APK_PATH, loaded.getApkPath());
        }
        super.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        if (isApkLoaded()) {
            intent.putExtra(ApkConstant.EXTRA_APK_PATH, loaded.getApkPath());
        }
        super.startActivity(intent, options);
    }

    @Override
    public boolean stopService(Intent name) {
        if (isApkLoaded()) {
            name.putExtra(ApkConstant.EXTRA_APK_PATH, loaded.getApkPath());
        }
        name = HookService_Component.modify(name);
        return super.stopService(name);
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        if (isApkLoaded()) {
            service.putExtra(ApkConstant.EXTRA_APK_PATH, loaded.getApkPath());
        }
        service = HookService_Component.modify(service);
        return super.bindService(service, conn, flags);
    }

    @Override
    public void sendBroadcast(Intent intent, String receiverPermission) {
        receiverPermission = getPackageName() + ".permission.APK_RECEIVE";
        super.sendBroadcast(intent, receiverPermission);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        sendBroadcast(intent, "");
    }

    @Override
    public Object getSystemService(String name) {
        if (Context.LAYOUT_INFLATER_SERVICE.equals(name)) {
            return new ApkLayoutInflater((LayoutInflater) super.getSystemService(name));
        } else if (ApkConstant.SERVICE_APK_BANDLE.equals(name)) {
            return null;
        }
        return super.getSystemService(name);
    }

}
