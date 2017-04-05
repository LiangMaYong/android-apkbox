package com.liangmayong.apkbox.hook.method;

import android.content.Intent;
import android.os.Message;

import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Field;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookLaunchActivity {

    private HookLaunchActivity() {
    }

    public static void handleLaunchActivity(Message msg) {
        ApkLogger.get().debug("hook handleLaunchActivity", null);
        Object obj = msg.obj;
        try {
            Field intent = obj.getClass().getDeclaredField("intent");
            intent.setAccessible(true);
            Intent raw = (Intent) intent.get(obj);
            if (raw.hasExtra(HookStartActivity.EXTRA_TARGET_INTENT)) {
                Intent target = raw.getParcelableExtra(HookStartActivity.EXTRA_TARGET_INTENT);
                intent.set(obj, target);
            }
        } catch (Exception e) {
        }
    }

}
