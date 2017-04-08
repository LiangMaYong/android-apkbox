package com.liangmayong.apkbox.hook.component;

import android.content.ComponentName;
import android.content.Intent;

import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.proxy.activity.Proxy0Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy10Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy1Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy2Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy3Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy404Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy4Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy5Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy6Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy7Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy8Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy9Activity;
import com.liangmayong.apkbox.utils.ApkLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookComponent_Activity {

    private HookComponent_Activity() {
    }

    // COMPONENTMAP
    private static Map<String, Class> COMPONENTMAP = new HashMap<>();
    // CLASSES
    public static Class<?>[] CLASSES = new Class[]{
            Proxy0Activity.class,
            Proxy1Activity.class,
            Proxy2Activity.class,
            Proxy3Activity.class,
            Proxy4Activity.class,
            Proxy5Activity.class,
            Proxy6Activity.class,
            Proxy7Activity.class,
            Proxy8Activity.class,
            Proxy9Activity.class,
            Proxy10Activity.class,
    };
    // index
    private static int index = 0;

    public static Intent modify(Intent raw) {
        if (!raw.hasExtra(ApkConstant.EXTRA_APK_MODIFIED)
                && raw != null
                && raw.getComponent() != null
                && raw.hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            String key = getPath(raw) + "@" + getClassName(raw);
            Class<?> clazz = null;
            if (raw.hasExtra(ApkConstant.EXTRA_APK_PROXY)) {
                String proxyClass = raw.getStringExtra(ApkConstant.EXTRA_APK_PROXY);
                try {
                    clazz = HookComponent_Activity.class.getClassLoader().loadClass(proxyClass);
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
            ApkLogger.get().debug("Activity proxy:" + clazz.getName(), null);
            ApkLogger.get().debug("Activity proxy:" + key, null);
            newIntent.setComponent(componentName);
            newIntent.putExtra(ApkConstant.EXTRA_APK_TARGET_INTENT, raw);
            newIntent.putExtra(ApkConstant.EXTRA_APK_MODIFIED, 1);
            return newIntent;
        }
        return raw;
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
