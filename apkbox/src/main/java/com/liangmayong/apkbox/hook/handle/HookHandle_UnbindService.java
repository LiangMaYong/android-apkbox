package com.liangmayong.apkbox.hook.handle;

import android.os.Message;

import com.liangmayong.apkbox.utils.ApkLogger;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookHandle_UnbindService {

    private HookHandle_UnbindService() {
    }

    public static void handleUnbindService(Message msg) {
        ApkLogger.get().debug("hook handleUnbindService", null);
    }

}
