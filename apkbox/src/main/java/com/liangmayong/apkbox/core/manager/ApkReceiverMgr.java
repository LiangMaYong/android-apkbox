package com.liangmayong.apkbox.core.manager;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.core.classloader.ApkClassLoader;
import com.liangmayong.apkbox.reflect.ApkReflect;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by liangmayong on 2017/3/31.
 */

public class ApkReceiverMgr {

    // plugin BROADCASTS
    private static final Map<String, LinkedList<BroadcastReceiver>> BROADCASTS = new HashMap<String, LinkedList<BroadcastReceiver>>();

    /**
     * unregisterReceiver
     *
     * @param apkPath apkPath
     */
    public static void unregisterReceiver(String apkPath) {
        String key = apkPath;
        if (BROADCASTS.containsKey(key)) {
            LinkedList<BroadcastReceiver> receivers = BROADCASTS.get(key);
            for (int i = 0; i < receivers.size(); i++) {
                ApkApplicationMgr.getHostApplication().unregisterReceiver(receivers.get(i));
            }
            BROADCASTS.remove(key);
        }
    }

    /**
     * registerReceiver
     *
     * @param apkPath apkPath
     */
    public static void registerReceiver(String apkPath) {
        String key = apkPath;
        if (BROADCASTS.containsKey(key)) {
            return;
        }
        ApkLoaded info = ApkLoaded.get(ApkApplicationMgr.getHostApplication(), apkPath);
        if (info != null) {
            Map<String, IntentFilter> filters = info.getFilters();
            LinkedList<BroadcastReceiver> receivers = new LinkedList<BroadcastReceiver>();
            for (Map.Entry<String, IntentFilter> entry : filters.entrySet()) {
                String clazzName = replaceClassName(info.getApkInfo().packageName, entry.getKey());
                Class<?> clazz = ApkClassLoader.loadClass(apkPath, clazzName);
                if (clazz != null && ApkReflect.isGeneric(clazz, BroadcastReceiver.class.getName())) {
                    try {
                        BroadcastReceiver broadcastReceiver = (BroadcastReceiver) clazz.newInstance();
                        receivers.add(broadcastReceiver);
                        ApkApplicationMgr.getHostApplication().registerReceiver(broadcastReceiver, entry.getValue());
                    } catch (Exception e) {
                    }
                }
            }
            BROADCASTS.put(key, receivers);
        }
    }

    /**
     * replaceClassName
     *
     * @param className className
     * @return className
     */
    private static String replaceClassName(String packageName, String className) {
        String newClassName = "";
        if (className.startsWith(".")) {
            newClassName = packageName + className;
        } else if (className.indexOf(".") == -1) {
            newClassName = packageName + "." + className;
        } else {
            newClassName = className;
        }
        return newClassName;
    }
}
