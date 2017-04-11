package com.liangmayong.apkbox.hook.modify;

import android.content.Intent;

import com.liangmayong.apkbox.core.constant.ApkConstant;

/**
 * Created by LiangMaYong on 2017/4/11.
 */

public class ApkComponentModifier {

    private ApkComponentModifier() {
    }

    public static String getKey(Intent intent) {
        return getPath(intent) + "@" + getClassName(intent);
    }

    public static String getPath(Intent intent) {
        if (intent != null && intent.hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            return intent.getStringExtra(ApkConstant.EXTRA_APK_PATH);
        }
        return null;
    }

    public static String getClassName(Intent intent) {
        if (intent != null && intent.getComponent() != null) {
            return intent.getComponent().getClassName();
        }
        return null;
    }
}
