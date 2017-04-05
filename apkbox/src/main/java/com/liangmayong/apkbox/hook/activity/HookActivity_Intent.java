package com.liangmayong.apkbox.hook.activity;

import android.app.Activity;
import android.content.Intent;

import com.liangmayong.apkbox.core.constant.ApkConstant;

/**
 * Created by LiangMaYong on 2017/4/5.
 */

public class HookActivity_Intent {

    private HookActivity_Intent() {
    }

    public static Intent modify(Activity target, Intent intent) {
        if (!intent.hasExtra(ApkConstant.EXTRA_APK_PATH) && target.getIntent().hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            intent.putExtra(ApkConstant.EXTRA_APK_PATH, target.getIntent().getStringExtra(ApkConstant.EXTRA_APK_PATH));
        }
        return intent;
    }

}
