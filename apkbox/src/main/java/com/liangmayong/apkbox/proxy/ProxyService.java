package com.liangmayong.apkbox.proxy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by LiangMaYong on 2017/4/6.
 */
public class ProxyService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("ProxyService", "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("ProxyService", "onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("ProxyService", "onStart");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.e("ProxyService", "onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("ProxyService", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("ProxyService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("ProxyService", "onDestroy");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.e("ProxyService", "onLowMemory");
    }
}
