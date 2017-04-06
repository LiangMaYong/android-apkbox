package com.liangmayong.apkbox.hook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.core.resources.ApkExtras;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookActivity_Intent {

    private HookActivity_Intent() {
    }

    public static Intent modify(Activity target, Intent intent) {
        if (!intent.hasExtra(ApkConstant.EXTRA_APK_PATH) && target.getIntent().hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                intent.replaceExtras(new Bundle());
                String extras_id = ApkExtras.putExtras(extras);
                intent.putExtra(ApkConstant.EXTRA_APK_EXTRAS, extras_id);
            }
            intent.putExtra(ApkConstant.EXTRA_APK_PATH, target.getIntent().getStringExtra(ApkConstant.EXTRA_APK_PATH));
        }
        return intent;
    }

}
