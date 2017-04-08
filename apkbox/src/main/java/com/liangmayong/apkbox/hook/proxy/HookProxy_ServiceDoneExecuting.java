package com.liangmayong.apkbox.hook.proxy;

import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Method;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookProxy_ServiceDoneExecuting {

    private HookProxy_ServiceDoneExecuting() {
    }

    public static Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ApkLogger.get().debug("hook " + method.getName(), null);
        try {
            return method.invoke(proxy, args);
        } catch (Exception e) {
        }
        return null;
    }

}
