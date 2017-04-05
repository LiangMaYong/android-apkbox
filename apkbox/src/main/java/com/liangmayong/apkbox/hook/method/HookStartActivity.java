package com.liangmayong.apkbox.hook.method;

import android.content.ComponentName;
import android.content.Intent;

import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.proxy.ProxyActivity;
import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Method;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookStartActivity {

    static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    private HookStartActivity() {
    }

    public static Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ApkLogger.get().debug("hook startActivity", null);
        int packageIndex = 1;
        int intentIndex = 2;
        String targetPackage = args[packageIndex] + "";
        Intent raw = (Intent) args[intentIndex];
        if (raw.hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            Intent newIntent = new Intent();
            ComponentName componentName = new ComponentName(targetPackage, ProxyActivity.class.getCanonicalName());
            newIntent.setComponent(componentName);
            newIntent.putExtra(EXTRA_TARGET_INTENT, raw);
            args[intentIndex] = newIntent;
        }
        return method.invoke(proxy, args);
    }

}
