package com.liangmayong.apkbox.proxy.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.IBinder;

import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.hook.service.HookService_Manager;
import com.liangmayong.apkbox.utils.ApkLogger;

/**
 * Created by LiangMaYong on 2017/4/8.
 */
public class Proxy404Service extends Service {

    private String TAG = getClass().getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        ApkLogger.get().debug(TAG + " - onBind", null);
        if (intent != null && intent.hasExtra(ApkConstant.EXTRA_APK_TARGET_INTENT)) {
            Intent target = intent.getParcelableExtra(ApkConstant.EXTRA_APK_TARGET_INTENT);
            return HookService_Manager.onBindService(this, target);
        }
        return new Proxy404Service.ProxyBinder();
    }

    @Override
    public void onCreate() {
        ApkLogger.get().debug(TAG + " - onCreate", null);
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        ApkLogger.get().debug(TAG + " - onStart", null);
        super.onStart(intent, startId);
        if (intent != null && intent.hasExtra(ApkConstant.EXTRA_APK_TARGET_INTENT)) {
            Intent target = intent.getParcelableExtra(ApkConstant.EXTRA_APK_TARGET_INTENT);
            HookService_Manager.onStartService(this, target, startId);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ApkLogger.get().debug(TAG + " - onStartCommandService", null);
        if (intent != null && intent.hasExtra(ApkConstant.EXTRA_APK_TARGET_INTENT)) {
            Intent target = intent.getParcelableExtra(ApkConstant.EXTRA_APK_TARGET_INTENT);
            return HookService_Manager.onStartCommandService(this, target, flags, startId);
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onRebind(Intent intent) {
        ApkLogger.get().debug(TAG + " - onRebind", null);
        if (intent != null && intent.hasExtra(ApkConstant.EXTRA_APK_TARGET_INTENT)) {
            Intent target = intent.getParcelableExtra(ApkConstant.EXTRA_APK_TARGET_INTENT);
            HookService_Manager.onRebindService(this, target);
        }
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        ApkLogger.get().debug(TAG + " - onUnbind", null);
        if (intent != null && intent.hasExtra(ApkConstant.EXTRA_APK_TARGET_INTENT)) {
            Intent target = intent.getParcelableExtra(ApkConstant.EXTRA_APK_TARGET_INTENT);
            return HookService_Manager.onUnbindService(this, target);
        }
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        ApkLogger.get().debug(TAG + " - onDestroy", null);
        super.onDestroy();
        HookService_Manager.onDestroyService(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        ApkLogger.get().debug(TAG + " - onConfigurationChanged", null);
        super.onConfigurationChanged(newConfig);
        HookService_Manager.onConfigurationChangedService(this, newConfig);
    }

    @Override
    public void onLowMemory() {
        ApkLogger.get().debug(TAG + " - onLowMemory", null);
        super.onLowMemory();
        HookService_Manager.onLowMemoryService(this);
    }

    @Override
    public void onTrimMemory(int level) {
        ApkLogger.get().debug(TAG + " - onTrimMemory", null);
        super.onTrimMemory(level);
        HookService_Manager.onTrimMemoryService(this, level);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        ApkLogger.get().debug(TAG + " - onTaskRemoved", null);
        super.onTaskRemoved(rootIntent);
        HookService_Manager.onTaskRemovedService(this, rootIntent);
    }

    private class ProxyBinder extends Binder {

        @Override
        public String toString() {
            return Proxy404Service.this.getClass().getName() + "$" + super.toString();
        }

    }
}
