package com.liangmayong.apkbox.hook.service;

import android.content.ComponentName;
import android.content.Intent;

import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.hook.activity.HookActivity_Component;
import com.liangmayong.apkbox.hook.modify.ApkComponentModifier;
import com.liangmayong.apkbox.proxy.service.Proxy00Service;
import com.liangmayong.apkbox.proxy.service.Proxy01Service;
import com.liangmayong.apkbox.proxy.service.Proxy02Service;
import com.liangmayong.apkbox.proxy.service.Proxy03Service;
import com.liangmayong.apkbox.proxy.service.Proxy04Service;
import com.liangmayong.apkbox.proxy.service.Proxy05Service;
import com.liangmayong.apkbox.proxy.service.Proxy06Service;
import com.liangmayong.apkbox.proxy.service.Proxy07Service;
import com.liangmayong.apkbox.proxy.service.Proxy08Service;
import com.liangmayong.apkbox.proxy.service.Proxy09Service;
import com.liangmayong.apkbox.proxy.service.Proxy10Service;
import com.liangmayong.apkbox.proxy.service.Proxy11Service;
import com.liangmayong.apkbox.proxy.service.Proxy12Service;
import com.liangmayong.apkbox.proxy.service.Proxy13Service;
import com.liangmayong.apkbox.proxy.service.Proxy14Service;
import com.liangmayong.apkbox.proxy.service.Proxy15Service;
import com.liangmayong.apkbox.proxy.service.Proxy16Service;
import com.liangmayong.apkbox.proxy.service.Proxy17Service;
import com.liangmayong.apkbox.proxy.service.Proxy18Service;
import com.liangmayong.apkbox.proxy.service.Proxy19Service;
import com.liangmayong.apkbox.proxy.service.Proxy20Service;
import com.liangmayong.apkbox.proxy.service.Proxy21Service;
import com.liangmayong.apkbox.proxy.service.Proxy22Service;
import com.liangmayong.apkbox.proxy.service.Proxy23Service;
import com.liangmayong.apkbox.proxy.service.Proxy24Service;
import com.liangmayong.apkbox.proxy.service.Proxy25Service;
import com.liangmayong.apkbox.proxy.service.Proxy26Service;
import com.liangmayong.apkbox.proxy.service.Proxy27Service;
import com.liangmayong.apkbox.proxy.service.Proxy28Service;
import com.liangmayong.apkbox.proxy.service.Proxy29Service;
import com.liangmayong.apkbox.proxy.service.Proxy30Service;
import com.liangmayong.apkbox.proxy.service.Proxy31Service;
import com.liangmayong.apkbox.proxy.service.Proxy32Service;
import com.liangmayong.apkbox.proxy.service.Proxy33Service;
import com.liangmayong.apkbox.proxy.service.Proxy404Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookService_Component {

    private HookService_Component() {
    }

    // COMPONENTMAP
    private static final Map<String, Class> COMPONENTMAP = new HashMap<>();
    // CLASSES
    private static final Class<?>[] CLASSES = new Class[]{
            Proxy00Service.class,
            Proxy01Service.class,
            Proxy02Service.class,
            Proxy03Service.class,
            Proxy04Service.class,
            Proxy05Service.class,
            Proxy06Service.class,
            Proxy07Service.class,
            Proxy08Service.class,
            Proxy09Service.class,
            Proxy10Service.class,
            Proxy11Service.class,
            Proxy12Service.class,
            Proxy13Service.class,
            Proxy14Service.class,
            Proxy15Service.class,
            Proxy16Service.class,
            Proxy17Service.class,
            Proxy18Service.class,
            Proxy19Service.class,
            Proxy20Service.class,
            Proxy21Service.class,
            Proxy22Service.class,
            Proxy23Service.class,
            Proxy24Service.class,
            Proxy25Service.class,
            Proxy26Service.class,
            Proxy27Service.class,
            Proxy28Service.class,
            Proxy29Service.class,
            Proxy30Service.class,
            Proxy31Service.class,
            Proxy32Service.class,
            Proxy33Service.class,
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
                        clazz = Proxy404Service.class;
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
