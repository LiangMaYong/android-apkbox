package com.liangmayong.apkbox.hook.handle;

import android.app.Service;

import com.liangmayong.apkbox.reflect.ApkReflect;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookActivity_Service {

    private HookActivity_Service() {
    }

    public static void putService(Object token, Service service) {
        Map map = getServices();
        if (map != null) {
            map.put(token, service);
        }
    }

    public static Service getService(Object token) {
        Map map = getServices();
        if (map != null) {
            return (Service) map.get(token);
        }
        return null;
    }

    public static void clearService() {
        Map map = getServices();
        if (map != null) {
            map.clear();
        }
    }

    private static Map getServices() {
        Map map = null;
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            currentActivityThreadField.setAccessible(true);
            Object currentActivityThread = currentActivityThreadField.get(null);
            map = (Map) ApkReflect.getField(activityThreadClass, currentActivityThread, "mServices");
        } catch (Exception e) {
        }
        return map;
    }
}
