package com.liangmayong.apkbox.hook.service;

import android.os.Handler;
import android.os.Message;

import com.liangmayong.apkbox.utils.ApkLogger;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookHandle_BindService {

    private HookHandle_BindService() {
    }

    public static void handleBindService(Handler handler, Message msg) {
        ApkLogger.get().debug("hook handleBindService", null);
    }

}
