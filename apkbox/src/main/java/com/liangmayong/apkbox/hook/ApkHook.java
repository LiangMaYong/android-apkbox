package com.liangmayong.apkbox.hook;

import android.app.Application;
import android.app.Instrumentation;
import android.content.pm.PackageManager;
import android.os.Handler;

import com.liangmayong.apkbox.reflect.ApkReflect;
import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class ApkHook {

    private ApkHook() {
    }

    public static void hook(Application application) {
        if (application == null) {
            return;
        }
        ApkLogger.get().debug("hook initialize", null);
        hookInstrumentation(application);
        hookPackageManager(application);
        hookActivityManagerNative(application);
        hookActivityThreadHandler(application);
    }

    private static void hookActivityThreadHandler(Application application) {
        try {
            Object currentActivityThread = HookActivityThread.getCurrentActivityThread(application);
            if (currentActivityThread != null) {
                Field mHField = currentActivityThread.getClass().getDeclaredField("mH");
                mHField.setAccessible(true);
                Handler mH = (Handler) mHField.get(currentActivityThread);


                Field mCallBackField = Handler.class.getDeclaredField("mCallback");
                mCallBackField.setAccessible(true);

                mCallBackField.set(mH, new HookActivityThreadHandlerCallback(mH));
            }
        } catch (Exception e) {
            ApkLogger.get().debug("hookActivityThreadHandler Exception", e);
        }
    }

    /**
     * hookActivityManagerNative
     *
     * @param application application
     */
    private static void hookActivityManagerNative(Application application) {
        try {
            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);

            Object gDefault = gDefaultField.get(null);

            Class<?> singleton = Class.forName("android.util.Singleton");
            Field mInstanceField = singleton.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            Object rawIActivityManager = mInstanceField.get(gDefault);

            Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{iActivityManagerInterface}, new HookActivityManagerHandler(rawIActivityManager));
            mInstanceField.set(gDefault, proxy);
        } catch (Exception e) {
            ApkLogger.get().debug("hookActivityManagerNative Exception", e);
        }
    }


    /**
     * hookPackageManager
     *
     * @param application application
     */
    private static void hookPackageManager(Application application) {
        try {
            Object currentActivityThread = HookActivityThread.getCurrentActivityThread(application);
            if (currentActivityThread != null) {
                Field sPackageManagerField = currentActivityThread.getClass().getDeclaredField("sPackageManager");
                sPackageManagerField.setAccessible(true);
                Object sPackageManager = sPackageManagerField.get(currentActivityThread);

                Class<?> iPackageManagerInterface = Class.forName("android.content.pm.IPackageManager");
                Object proxy = Proxy.newProxyInstance(iPackageManagerInterface.getClassLoader(),
                        new Class<?>[]{iPackageManagerInterface},
                        new HookPackageManagerHandler(sPackageManager));

                sPackageManagerField.set(currentActivityThread, proxy);

                PackageManager pm = application.getPackageManager();
                Field mPmField = pm.getClass().getDeclaredField("mPM");
                mPmField.setAccessible(true);
                mPmField.set(pm, proxy);
            }
        } catch (Exception e) {
            ApkLogger.get().debug("hookPackageManager Exception", e);
        }
    }


    /**
     * hookInstrumentation
     *
     * @param application application
     * @return true or false
     */
    private static void hookInstrumentation(Application application) {
        try {

            Object currentActivityThread = HookActivityThread.getCurrentActivityThread(application);
            if (currentActivityThread != null) {
                Instrumentation rawInstrumentation = (Instrumentation) ApkReflect.getField(currentActivityThread.getClass(),
                        currentActivityThread, "mInstrumentation");
                HookActivityInstrumentationHnadler instrumentation = new HookActivityInstrumentationHnadler(rawInstrumentation);
                ApkReflect.setField(currentActivityThread.getClass(), currentActivityThread, "mInstrumentation", instrumentation);
            }
        } catch (Exception e) {
            ApkLogger.get().debug("hookInstrumentation Exception", e);
        }
    }

}
