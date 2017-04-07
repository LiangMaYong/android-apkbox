package com.liangmayong.apkbox.hook.service;

import android.os.Handler;
import android.os.Message;

import com.liangmayong.apkbox.utils.ApkLogger;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookHandle_UnbindService {

    private HookHandle_UnbindService() {
    }

    public static void handleUnbindService(Handler hnadler, Message msg) {
        ApkLogger.get().debug("hook handleUnbindService", null);
    }

}
