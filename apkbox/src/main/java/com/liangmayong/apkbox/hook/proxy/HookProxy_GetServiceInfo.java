package com.liangmayong.apkbox.hook.proxy;

import android.content.pm.ServiceInfo;

import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Method;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookProxy_GetServiceInfo {

    private HookProxy_GetServiceInfo() {
    }

    public static Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ApkLogger.get().debug("hook proxy " + method.getName(), null);
        try {
            Object actInfo = method.invoke(proxy, args);
            if (actInfo != null) {
                return actInfo;
            }
        } catch (Exception e) {
        }
        return new ServiceInfo();
    }

}
