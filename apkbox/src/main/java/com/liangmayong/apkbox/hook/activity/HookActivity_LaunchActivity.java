package com.liangmayong.apkbox.hook.activity;

import android.content.Intent;
import android.os.Message;

import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.utils.ApkBuild;
import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Field;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookActivity_LaunchActivity {

    private static final boolean DEBUG_HOOK_PROXY = ApkBuild.DEBUG_HOOK_PROXY;

    private HookActivity_LaunchActivity() {
    }

    public static void handleLaunchActivity(Message msg) {
        if (DEBUG_HOOK_PROXY)
            ApkLogger.get().debug("hook handleLaunchActivity", null);
        Object obj = msg.obj;
        try {
            Field intent = obj.getClass().getDeclaredField("intent");
            intent.setAccessible(true);
            Intent raw = (Intent) intent.get(obj);
            if (raw.hasExtra(ApkConstant.EXTRA_APK_TARGET_INTENT)) {
                Intent target = raw.getParcelableExtra(ApkConstant.EXTRA_APK_TARGET_INTENT);
                intent.set(obj, target);
            }
        } catch (Exception e) {
        }
    }

}
