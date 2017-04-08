package com.liangmayong.apkbox.hook.service;

import android.os.Handler;
import android.os.Message;

import com.liangmayong.apkbox.utils.ApkLogger;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookHandle_StopService {

    private HookHandle_StopService() {
    }

    public static void handleStopService(Handler handler, Message msg) {
        ApkLogger.get().debug("hook handle stopService", null);
    }


}
