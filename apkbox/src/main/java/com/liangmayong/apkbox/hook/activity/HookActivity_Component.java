package com.liangmayong.apkbox.hook.activity;

import android.content.ComponentName;
import android.content.Intent;

import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.proxy.activity.Proxy0Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy404Activity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookActivity_Component {

    private HookActivity_Component() {
    }

    // COMPONENTMAP
    private static Map<String, Class> COMPONENTMAP = new HashMap<>();
    // CLASSES
    public static Class<?>[] CLASSES = new Class[]{
            Proxy0Activity.class
    };
    // index
    private static int index = 0;

    public static Intent modify(Intent raw) {
        if (!raw.hasExtra(ApkConstant.EXTRA_APK_MODIFIED)
                && raw != null
                && raw.getComponent() != null
                && raw.hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            String key = getKey(raw);
            Class<?> clazz = null;
            if (raw.hasExtra(ApkConstant.EXTRA_APK_PROXY)) {
                String proxyClass = raw.getStringExtra(ApkConstant.EXTRA_APK_PROXY);
                try {
                    clazz = HookActivity_Component.class.getClassLoader().loadClass(proxyClass);
                } catch (ClassNotFoundException e) {
                }
            }
            if (clazz == null) {
                if (COMPONENTMAP.containsKey(key)) {
                    clazz = COMPONENTMAP.get(key);
                } else {
                    if (index >= CLASSES.length) {
                        clazz = Proxy404Activity.class;
                    } else {
                        clazz = CLASSES[index];
                        COMPONENTMAP.put(key, clazz);
                    }
                    index++;
                }
            }
            Intent newIntent = new Intent();
            ComponentName componentName = new ComponentName(raw.getComponent().getPackageName(), clazz.getName());
            newIntent.setComponent(componentName);
            newIntent.putExtra(ApkConstant.EXTRA_APK_TARGET_INTENT, raw);
            newIntent.putExtra(ApkConstant.EXTRA_APK_MODIFIED, 1);
            return newIntent;
        }
        return raw;
    }

    public static String getKey(Intent intent) {
        return getPath(intent) + "@" + getClassName(intent);
    }

    public static String getPath(Intent intent) {
        if (intent != null && intent.hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            return intent.getStringExtra(ApkConstant.EXTRA_APK_PATH);
        }
        return null;
    }

    public static String getClassName(Intent intent) {
        if (intent != null && intent.getComponent() != null) {
            return intent.getComponent().getClassName();
        }
        return null;
    }
}
