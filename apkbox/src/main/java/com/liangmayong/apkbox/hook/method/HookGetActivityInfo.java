package com.liangmayong.apkbox.hook.method;

import android.content.pm.ActivityInfo;

import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Method;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookGetActivityInfo {

    private HookGetActivityInfo() {
    }

    public static Object getActivityInfo(Object proxy, Method method, Object[] args) throws Throwable {
        ApkLogger.get().debug("hook getActivityInfo", null);
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
