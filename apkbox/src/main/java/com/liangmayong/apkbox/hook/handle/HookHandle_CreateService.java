package com.liangmayong.apkbox.hook.handle;

import android.os.Message;

import com.liangmayong.apkbox.utils.ApkLogger;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookHandle_CreateService {

    private HookHandle_CreateService() {
    }

    public static void handleCreateService(Message msg) {
        ApkLogger.get().debug("hook handleCreateService", null);
    }

}
