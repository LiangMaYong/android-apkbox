package com.liangmayong.apkbox.hook.service;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Field;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookHandle_ServiceArgs {

    private HookHandle_ServiceArgs() {
    }

    public static void handleServiceArgs(Handler handler, Message msg) {
        handler.handleMessage(msg);
        handleRealServiceArgs(msg);
    }

    public static void handleRealServiceArgs(Message msg) {
        ApkLogger.get().debug("hook handle serviceArgs", null);
        Object obj = msg.obj;
        try {
            Field intent = obj.getClass().getDeclaredField("args");
            intent.setAccessible(true);
            Intent raw = (Intent) intent.get(obj);
            if (raw.hasExtra(ApkConstant.EXTRA_APK_TARGET_INTENT)) {
                Intent target = raw.getParcelableExtra(ApkConstant.EXTRA_APK_TARGET_INTENT);
                Field token = obj.getClass().getDeclaredField("token");
                token.setAccessible(true);
                Object proxyToken = token.get(obj);
                HookService_ServiceManager.createRealService(proxyToken, target);
            }
        } catch (Exception e) {
        }
    }

}
