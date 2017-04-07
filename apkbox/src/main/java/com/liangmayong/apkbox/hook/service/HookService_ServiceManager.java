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

    private static final Map<String, Object> mServiceTokens = new HashMap<String, Object>();

    public static Object createService(Object token, Intent intent) {
        if (intent.hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            String apkPath = intent.getStringExtra(ApkConstant.EXTRA_APK_PATH);
            String className = intent.getComponent().getClassName();
            String key = apkPath + "@" + className;
            if (mServiceTokens.containsKey(key)) {
                return mServiceTokens.get(key);
            } else {
                try {
                    HookHandle_IBinder iBinder = new HookHandle_IBinder();
                    Object serviceToken = iBinder.getToken(token);
                    Service proxyService = HookService_SystemServices.getService(token);
                    Service rawService = handleCreateService(serviceToken, proxyService, apkPath, className);
                    HookService_SystemServices.putService(serviceToken, rawService);
                    mServiceTokens.put(key, serviceToken);
                    return serviceToken;
                } catch (Exception e) {
                }
            }
        }
        return token;
    }

    public static Object stopService(Intent intent) {
        if (intent.hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            String apkPath = intent.getStringExtra(ApkConstant.EXTRA_APK_PATH);
            String className = intent.getComponent().getClassName();
            String key = apkPath + "@" + className;
            if (mServiceTokens.containsKey(key)) {
                Object stoken = mServiceTokens.get(key);
                mServiceTokens.remove(key);
                return stoken;
            }
        }
        return null;
    }


    public static Service handleCreateService(Object serviceToken, Service proxyService, String apkPath, String serviceName) {
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
            attachMethod.invoke(ctx, currentActivityThread, serviceToken, application, activityManager);

            ApkMethod attachBaseContextMethod = new ApkMethod(Service.class, rawService, "attachBaseContext", Context.class);
            attachBaseContextMethod.invoke(ctx);

            rawService.onCreate();
            return rawService;
        } catch (Exception e) {
        }
        return proxyService;
    }

}
