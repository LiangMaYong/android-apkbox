package com.liangmayong.apkbox.hook.service;

import android.app.Service;

import com.liangmayong.apkbox.hook.HookActivityThread;
import com.liangmayong.apkbox.reflect.ApkReflect;
import com.liangmayong.apkbox.utils.ApkLogger;

import java.util.Map;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookService_SystemServices {

    private HookService_SystemServices() {
    }

    public static Service getService(Object token) {
        Map map = getServices();
        if (map != null) {
            return (Service) map.get(token);
        }
        return null;
    }

    private static Map getServices() {
        Map map = null;
        try {
            Object currentActivityThread = HookActivityThread.getCurrentActivityThread(null);
            map = (Map) ApkReflect.getField(currentActivityThread.getClass(), currentActivityThread, "mServices");
        } catch (Exception e) {
        }
        return map;
    }
}
