package com.liangmayong.apkbox.hook.handle;

import android.os.Message;

import com.liangmayong.apkbox.utils.ApkLogger;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookHandle_StopService {

    private HookHandle_StopService() {
    }

    public static void handleStopService(Message msg) {
        ApkLogger.get().debug("hook handleStopService", null);
    }

}
