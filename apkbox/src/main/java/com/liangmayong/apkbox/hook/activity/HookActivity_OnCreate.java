package com.liangmayong.apkbox.hook.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.hook.modify.ApkActivityModifier;
import com.liangmayong.apkbox.hook.modify.ApkLayoutInflaterModifier;

/**
 * Created by LiangMaYong on 2017/4/5.
 */

public class HookActivity_OnCreate {

    private HookActivity_OnCreate() {
    }

    public static void onCreate(Activity target, Bundle icicle) {
        ApkLayoutInflaterModifier.modify();
        if (target.getIntent().hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            String apkPath = target.getIntent().getStringExtra(ApkConstant.EXTRA_APK_PATH);
            ApkActivityModifier.modifyActivity(target, apkPath);
        }
        Log.e("TAG", "onCreate");
    }

}
