package com.liangmayong.android_apkbox;

import android.app.Application;

import com.liangmayong.apkbox.ApkBox;

/**
 * Created by LiangMaYong on 2017/3/30.
 */

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApkBox.get().initialize(this);
    }
}
