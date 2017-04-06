package com.liangmayong.apkbox.hook.handle;

import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Method;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookProxy_StartService {

    private HookProxy_StartService() {
    }

    public static Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ApkLogger.get().debug("hook startService", null);
        return method.invoke(proxy, args);
    }

}
