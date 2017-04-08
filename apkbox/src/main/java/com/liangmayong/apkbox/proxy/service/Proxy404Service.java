package com.liangmayong.apkbox.proxy.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.liangmayong.apkbox.hook.service.HookService_ServiceManager;
import com.liangmayong.apkbox.utils.ApkLogger;

/**
 * Created by LiangMaYong on 2017/4/8.
 */
public class Proxy404Service extends Service {

    private String TAG = getClass().getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        ApkLogger.get().debug(TAG + " - onBind", null);
        return new Proxy404Service.ProxyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ApkLogger.get().debug(TAG + " - onCreate", null);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        ApkLogger.get().debug(TAG + " - onStart", null);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        ApkLogger.get().debug(TAG + " - onRebind", null);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        ApkLogger.get().debug(TAG + " - onUnbind", null);
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ApkLogger.get().debug(TAG + " - onStartCommand", null);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ApkLogger.get().debug(TAG + " - onDestroy", null);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        HookService_ServiceManager.onLowMemory();
        ApkLogger.get().debug(TAG + " - onLowMemory", null);
    }

    private class ProxyBinder extends Binder {

        @Override
        public String toString() {
            return Proxy404Service.this.getClass().getName() + "$" + super.toString();
        }
    }
}
