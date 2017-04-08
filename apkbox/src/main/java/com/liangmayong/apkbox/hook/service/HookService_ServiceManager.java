package com.liangmayong.apkbox.hook.service;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.reflect.ApkMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookService_ServiceManager {

    private HookService_ServiceManager() {
    }

    private static final Map<Object, Service> mProxyServices = new HashMap<Object, Service>();
    private static final Map<String, Object> mServiceTokens = new HashMap<String, Object>();
    private static final Map<String, Service> mRealServices = new HashMap<String, Service>();

    public static Object createRealService(Object token, Intent intent) {
        if (intent.hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            String apkPath = HookService_Component.getPath(intent);
            String className = HookService_Component.getClassName(intent);
            String key = HookService_Component.getKey(intent);
            if (mRealServices.containsKey(key)) {
                return mServiceTokens.get(key);
            } else {
                try {
                    Service proxyService = null;
                    if (mProxyServices.containsKey(token)) {
                        proxyService = mProxyServices.get(token);
                    } else {
                        proxyService = HookService_SystemServices.getService(token);
                        mProxyServices.put(token, proxyService);
                    }
                    Object realToken = token;
                    Service rawService = handleCreateService(realToken, proxyService, apkPath, className);
                    HookService_SystemServices.putService(realToken, rawService);
                    mRealServices.put(key, rawService);
                    mServiceTokens.put(key, realToken);
                    return realToken;
                } catch (Exception e) {
                }
            }
        }
        return token;
    }

    public static Object stopService(Intent intent) {
        if (intent.hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            String key = HookService_Component.getKey(intent);
            if (mRealServices.containsKey(key)) {
                Object stoken = mServiceTokens.get(key);
                Service rawService = mRealServices.get(key);
                mRealServices.remove(key);
                mServiceTokens.remove(key);
                rawService.onDestroy();
                return stoken;
            }
        }
        return null;
    }

    public static Object resetProxyService(Object token) {
        if (token != null) {
            Service proxyService = mProxyServices.get(token);
            HookService_SystemServices.putService(token, proxyService);
        }
        return token;
    }

    public static void onLowMemory() {
        for (Map.Entry<String, Service> entry : mRealServices.entrySet()) {
            entry.getValue().onLowMemory();
        }
    }

    public static Service handleCreateService(Object realToken, Service proxyService, String apkPath, String serviceName) {
        try {
            ApkLoaded loaded = ApkLoaded.get(proxyService.getBaseContext(), apkPath);
            Context ctx = loaded.getContext(proxyService.getBaseContext());
            Application application = loaded.getApkApplication();


            Service rawService = (Service) loaded.getClassLoader().loadClass(serviceName).newInstance();
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
            Method getDefaultMethod = activityManagerNativeClass.getDeclaredMethod("getDefault");
            Object activityManager = getDefaultMethod.invoke(null);

            ApkMethod attachMethod = new ApkMethod(Service.class, rawService, "attach", Context.class, activityThreadClass, IBinder.class, Application.class, activityManagerNativeClass);
            attachMethod.invoke(ctx, currentActivityThread, realToken, application, activityManager);

            ApkMethod attachBaseContextMethod = new ApkMethod(Service.class, rawService, "attachBaseContext", Context.class);
            attachBaseContextMethod.invoke(ctx);

            rawService.onCreate();
            return rawService;
        } catch (Exception e) {
        }
        return proxyService;
    }

}
