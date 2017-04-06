package com.liangmayong.apkbox.hook.handle;

import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Method;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookProxy_StopService {

    private HookProxy_StopService() {
    }

    public static Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ApkLogger.get().debug("hook stopService", null);
        return method.invoke(proxy, args);
    }

}
