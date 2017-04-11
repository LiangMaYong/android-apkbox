package com.liangmayong.apkbox.hook.service;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.hook.HookCurrentActivityThread;
import com.liangmayong.apkbox.hook.modify.ApkComponentModifier;
import com.liangmayong.apkbox.reflect.ApkMethod;
import com.liangmayong.apkbox.reflect.ApkReflect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookService_Manager {

    private HookService_Manager() {
    }

    private static final Map<Service, Map<String, Service>> mRealServices = new HashMap<Service, Map<String, Service>>();

    public static boolean onUnbindService(Service service, Intent intent) {
        if (mRealServices.containsKey(service)) {
            Map<String, Service> serviceMap = mRealServices.get(service);
            String key = ApkComponentModifier.getKey(intent);
            if (serviceMap.containsKey(key)) {
                return serviceMap.get(key).onUnbind(intent);
            }
        }
        return false;
    }

    public static IBinder onBindService(Service service, Intent intent) {
        if (mRealServices.containsKey(service)) {
            Map<String, Service> serviceMap = mRealServices.get(service);
            String key = ApkComponentModifier.getKey(intent);
            if (serviceMap.containsKey(key)) {
                return serviceMap.get(key).onBind(intent);
            }
        }
        return null;
    }

    public static void onStartService(Service service, Intent intent, int startId) {
        realCreateService(service, intent);
        if (mRealServices.containsKey(service)) {
            Map<String, Service> serviceMap = mRealServices.get(service);
            String key = ApkComponentModifier.getKey(intent);
            if (serviceMap.containsKey(key)) {
                serviceMap.get(key).onStart(intent, startId);
            }
        }
    }

    public static int onStartCommandService(Service service, Intent intent, int flags, int startId) {
        realCreateService(service, intent);
        if (mRealServices.containsKey(service)) {
            Map<String, Service> serviceMap = mRealServices.get(service);
            String key = ApkComponentModifier.getKey(intent);
            if (serviceMap.containsKey(key)) {
                return serviceMap.get(key).onStartCommand(intent, flags, startId);
            }
        }
        return Service.START_STICKY;
    }

    public static void onRebindService(Service service, Intent intent) {
        if (mRealServices.containsKey(service)) {
            Map<String, Service> serviceMap = mRealServices.get(service);
            String key = ApkComponentModifier.getKey(intent);
            if (serviceMap.containsKey(key)) {
                serviceMap.get(key).onRebind(intent);
            }
        }
    }

    public static void onConfigurationChangedService(Service service, Configuration newConfig) {
        if (mRealServices.containsKey(service)) {
            Map<String, Service> serviceMap = mRealServices.get(service);
            for (Map.Entry<String, Service> entry :
                    serviceMap.entrySet()) {
                entry.getValue().onConfigurationChanged(newConfig);
            }
        }
    }

    public static void onTrimMemoryService(Service service, int level) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return;
        }
        if (mRealServices.containsKey(service)) {
            Map<String, Service> serviceMap = mRealServices.get(service);
            for (Map.Entry<String, Service> entry :
                    serviceMap.entrySet()) {
                entry.getValue().onTrimMemory(level);
            }
        }
    }

    public static void onTaskRemovedService(Service service, Intent rootIntent) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return;
        }
        if (mRealServices.containsKey(service)) {
            Map<String, Service> serviceMap = mRealServices.get(service);
            for (Map.Entry<String, Service> entry :
                    serviceMap.entrySet()) {
                entry.getValue().onTaskRemoved(rootIntent);
            }
        }
    }

    public static void onLowMemoryService(Service service) {
        if (mRealServices.containsKey(service)) {
            Map<String, Service> serviceMap = mRealServices.get(service);
            for (Map.Entry<String, Service> entry :
                    serviceMap.entrySet()) {
                entry.getValue().onLowMemory();
            }
        }
    }

    public static void onDestroyService(Service service) {
        if (mRealServices.containsKey(service)) {
            Map<String, Service> serviceMap = mRealServices.get(service);
            for (Map.Entry<String, Service> entry :
                    serviceMap.entrySet()) {
                entry.getValue().onDestroy();
            }
        }
    }

    private static void realCreateService(Service service, Intent intent) {
        if (intent.hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            String apkPath = ApkComponentModifier.getPath(intent);
            String className = ApkComponentModifier.getClassName(intent);
            String key = ApkComponentModifier.getKey(intent);
            if (!mRealServices.containsKey(service) || !mRealServices.get(service).containsKey(key)) {
                try {
                    Object realToken = new Binder();
                    Service rawService = handleCreateService(realToken, service, apkPath, className);
                    if (mRealServices.containsKey(service)) {
                        mRealServices.get(service).put(key, rawService);
                    } else {
                        Map<String, Service> serviceMap = new HashMap<>();
                        serviceMap.put(key, rawService);
                        mRealServices.put(service, serviceMap);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private static Service handleCreateService(Object realToken, Service proxyService, String apkPath, String serviceName) {
        try {
            ApkLoaded loaded = ApkLoaded.get(proxyService.getBaseContext(), apkPath);
            Context ctx = loaded.getContext(proxyService.getBaseContext());
            Application application = loaded.getApkApplication();


            Service rawService = (Service) loaded.getClassLoader().loadClass(serviceName).newInstance();
            Object currentActivityThread = HookCurrentActivityThread.getCurrentActivityThread(application);

            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
            Method getDefaultMethod = activityManagerNativeClass.getDeclaredMethod("getDefault");
            Object activityManager = getDefaultMethod.invoke(null);

            ApkMethod attachMethod = new ApkMethod(Service.class, rawService, "attach", Context.class, currentActivityThread.getClass(), IBinder.class, Application.class, activityManagerNativeClass);
            attachMethod.invoke(ctx, currentActivityThread, realToken, application, activityManager);

            ApkMethod attachBaseContextMethod = new ApkMethod(Service.class, rawService, "attachBaseContext", Context.class);
            attachBaseContextMethod.invoke(ctx);

            modifyService(application, serviceName, realToken, ctx.getApplicationInfo().targetSdkVersion < Build.VERSION_CODES.ECLAIR, rawService);

            rawService.onCreate();
            return rawService;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proxyService;
    }

    private static void modifyService(Application application, String serviceName, Object token, boolean compatibility, Service service) {
        try {
            ApkReflect.setField(Service.class, service, "mClassName", serviceName);
            ApkReflect.setField(Service.class, service, "mToken", token);
            ApkReflect.setField(Service.class, service, "mApplication", application);
            ApkReflect.setField(Service.class, service, "mStartCompatibility", compatibility);

            Object currentActivityThread = HookCurrentActivityThread.getCurrentActivityThread(application);
            ApkReflect.setField(Service.class, service, "mThread", currentActivityThread);

            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
            Method getDefaultMethod = activityManagerNativeClass.getDeclaredMethod("getDefault");
            Object activityManager = getDefaultMethod.invoke(null);
            ApkReflect.setField(Service.class, service, "mActivityManager", activityManager);
        } catch (Exception e) {
        }
    }
}
