package com.liangmayong.apkbox.hook.handle;

import android.os.Message;

import com.liangmayong.apkbox.utils.ApkLogger;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookHandle_ServiceArgs {

    private HookHandle_ServiceArgs() {
    }

    public static void handleServiceArgs(Message msg) {
        ApkLogger.get().debug("hook handleServiceArgs", null);
    }

}
