package com.liangmayong.apkbox.hook.service;

import android.content.ComponentName;
import android.content.Intent;

import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.proxy.ProxyService;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookService_Component {

    private HookService_Component() {
    }

    public static Intent modify(Intent raw) {
        if (!raw.hasExtra(ApkConstant.EXTRA_APK_MODIFIED)
                && raw != null
                && raw.getComponent() != null
                && raw.hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            Intent newIntent = new Intent();
            ComponentName componentName = new ComponentName(raw.getComponent().getPackageName(), ProxyService.class.getCanonicalName());
            newIntent.setComponent(componentName);
            newIntent.putExtra(ApkConstant.EXTRA_APK_TARGET_INTENT, raw);
            newIntent.putExtra(ApkConstant.EXTRA_APK_MODIFIED, 1);
            return newIntent;
        }
        return raw;
    }

}
