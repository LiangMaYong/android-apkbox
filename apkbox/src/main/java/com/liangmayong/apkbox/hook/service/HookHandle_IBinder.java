package com.liangmayong.apkbox.hook.service;

import android.os.Binder;

import java.lang.reflect.Constructor;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookHandle_IBinder {

    public HookHandle_IBinder() {
    }

    public Object getToken(Object token) {
        return new Binder();
    }

}
