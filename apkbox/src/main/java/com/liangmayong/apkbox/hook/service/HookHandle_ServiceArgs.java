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

    public static void handleServiceArgs(Handler hnadler, Message msg) {
        hnadler.handleMessage(msg);
        ApkLogger.get().debug("hook handleServiceArgs", null);
        Object obj = msg.obj;
        try {
            Field intent = obj.getClass().getDeclaredField("args");
            intent.setAccessible(true);
            Intent raw = (Intent) intent.get(obj);
            if (raw.hasExtra(ApkConstant.EXTRA_APK_TARGET_INTENT)) {
                Intent target = raw.getParcelableExtra(ApkConstant.EXTRA_APK_TARGET_INTENT);
                intent.set(obj, target);
                Field token = obj.getClass().getDeclaredField("token");
                token.setAccessible(true);
                Object serviceToken = HookService_ServiceManager.createService(token.get(obj), target);
                token.set(obj, serviceToken);
            }
        } catch (Exception e) {
        }
    }

}
