package com.liangmayong.apkbox.core.manager;

import android.app.Application;
import android.content.Context;

import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.core.context.ApkContext;
import com.liangmayong.apkbox.core.utils.ApkLogger;
import com.liangmayong.apkbox.reflect.ApkMethod;
import com.liangmayong.apkbox.reflect.ApkReflect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangmayong on 2017/3/31.
 */
public class ApkApplicationMgr {

    private static final Map<String, Application> APPLICATIONS = new HashMap<String, Application>();

    /**
     * handleCreateApplication
     *
     * @param apkPath apkPath
     * @return application
     */
    public synchronized static Application handleCreateApplication(final String apkPath) {
        Context context = getHostApplication();
        String key = apkPath;
        if (APPLICATIONS.containsKey(key)) {
            return APPLICATIONS.get(key);
        }
        Application application = null;
        ApkLoaded info = ApkLoaded.get(context, apkPath);
        if (info == null) {
            application = getHostApplication();
        } else {
            Context ctx = ApkContext.get(context, apkPath);
            try {
                application = (Application) info.getPackage().getClassLoader().loadClass(info.getApkApp()).newInstance();
                APPLICATIONS.put(key, application);
                ApkMethod method = new ApkMethod(Application.class, application, "attach", Context.class);
                method.invoke(ctx);
                ApkReflect.setField(Application.class, application, "mBase", ctx);
                ApkReceiverMgr.registerReceiver(apkPath);
                application.onCreate();
                ApkLogger.get().debug("Create application : " + info.getApkApp(), null);
            } catch (Exception e) {
                ApkLogger.get().debug("Create  application fail : " + info.getApkApp(), null);
                application = (Application) ctx;
            }
        }
        return application;
    }


    // application
    private static Application application = null;

    /**
     * getHostApplication
     *
     * @return application
     */
    public static Application getHostApplication() {
        if (application == null) {
            synchronized (ApkApplicationMgr.class) {
                if (application == null) {
                    try {
                        Class<?> clazz = Class.forName("android.app.ActivityThread");
                        Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
                        if (currentActivityThread != null) {
                            Object object = currentActivityThread.invoke(null);
                            if (object != null) {
                                Method getApplication = object.getClass().getDeclaredMethod("getApplication");
                                if (getApplication != null) {
                                    application = (Application) getApplication.invoke(object);
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return application;
    }
}
