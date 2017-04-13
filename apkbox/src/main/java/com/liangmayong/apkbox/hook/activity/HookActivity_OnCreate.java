package com.liangmayong.apkbox.hook.activity;

import android.app.Activity;
import android.os.Bundle;

import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.core.resources.ApkExtras;
import com.liangmayong.apkbox.hook.modify.ApkActivityInfoModifier;
import com.liangmayong.apkbox.hook.modify.ApkActivityModifier;
import com.liangmayong.apkbox.hook.modify.ApkLayoutInflaterModifier;

/**
 * Created by LiangMaYong on 2017/4/5.
 */

public class HookActivity_OnCreate {

    private HookActivity_OnCreate() {
    }

    public static void onCreate(Activity target, Bundle icicle, boolean after) {
        if (after) {
            onCreateAfter(target);
        } else {
            onCreateBefore(target);
        }
    }

    private static void onCreateAfter(Activity target) {
        // onCreateAfter
    }

    private static void onCreateBefore(Activity target) {
        String apkPath = "";
        if (target.getIntent().hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            apkPath = target.getIntent().getStringExtra(ApkConstant.EXTRA_APK_PATH);
            ApkActivityModifier.modify(target, apkPath);
            ApkActivityInfoModifier.modify(target, apkPath);
        }
        ApkLayoutInflaterModifier.modify(apkPath);
        if (target.getIntent().hasExtra(ApkConstant.EXTRA_APK_EXTRAS)) {
            String extras_id = target.getIntent().getStringExtra(ApkConstant.EXTRA_APK_EXTRAS);
            Bundle extras = ApkExtras.getExtras(extras_id);
            if (extras != null) {
                target.getIntent().putExtras(extras);
            }
            target.getIntent().removeExtra(ApkConstant.EXTRA_APK_EXTRAS);
        }
    }
}
