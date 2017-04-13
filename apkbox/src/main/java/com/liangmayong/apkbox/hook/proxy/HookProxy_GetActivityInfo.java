package com.liangmayong.apkbox.hook.proxy;

import android.content.pm.ActivityInfo;

import com.liangmayong.apkbox.utils.ApkBuild;
import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Method;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookProxy_GetActivityInfo {

    private static final boolean DEBUG_HOOK_PROXY = ApkBuild.DEBUG_HOOK_PROXY;

    private HookProxy_GetActivityInfo() {
    }

    public static Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (DEBUG_HOOK_PROXY)
            ApkLogger.get().debug("hook proxy " + method.getName(), null);
        try {
            Object actInfo = method.invoke(proxy, args);
            if (actInfo != null) {
                return actInfo;
            }
        } catch (Exception e) {
        }
        return new ActivityInfo();
    }

}
