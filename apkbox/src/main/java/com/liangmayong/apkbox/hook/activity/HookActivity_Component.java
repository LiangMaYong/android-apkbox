package com.liangmayong.apkbox.hook.activity;

import android.content.ComponentName;
import android.content.Intent;

import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.hook.modify.ApkComponentModifier;
import com.liangmayong.apkbox.proxy.activity.Proxy00Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy01Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy02Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy03Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy04Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy05Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy06Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy07Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy08Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy09Activity;
import com.liangmayong.apkbox.proxy.activity.Proxy10Activity;
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
    private static final Map<String, Class> COMPONENTMAP = new HashMap<>();
    // CLASSES
    private static final Class<?>[] CLASSES = new Class[]{
            Proxy00Activity.class,
            Proxy01Activity.class,
            Proxy02Activity.class,
            Proxy03Activity.class,
            Proxy04Activity.class,
            Proxy05Activity.class,
            Proxy06Activity.class,
            Proxy07Activity.class,
            Proxy08Activity.class,
            Proxy09Activity.class,
            Proxy10Activity.class,
    };
    // index
    private static int index = 0;

    public static Intent modify(Intent raw) {
        if (!raw.hasExtra(ApkConstant.EXTRA_APK_MODIFIED)
                && raw != null
                && raw.getComponent() != null
                && raw.hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            String key = ApkComponentModifier.getKey(raw);
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
            newIntent.putExtra(ApkConstant.EXTRA_APK_MODIFIED, clazz.getName());
            return newIntent;
        }
        return raw;
    }

}
