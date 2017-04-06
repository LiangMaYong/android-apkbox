package com.liangmayong.apkbox.hook.activity;

import android.content.ComponentName;
import android.content.Intent;

import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.proxy.ProxyActivity;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookActivity_Component {

    private HookActivity_Component() {
    }

    public static Intent modify(Intent raw, String targetPackage) {
        if (!raw.hasExtra(ApkConstant.EXTRA_APK_MODIFIED)
                && raw != null
                && raw.getComponent() != null
                && raw.hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            Intent newIntent = new Intent();
            ComponentName componentName = new ComponentName(targetPackage, ProxyActivity.class.getCanonicalName());
            newIntent.setComponent(componentName);
            newIntent.putExtra(ApkConstant.EXTRA_APK_TARGET_INTENT, raw);
            newIntent.putExtra(ApkConstant.EXTRA_APK_MODIFIED, 1);
            return newIntent;
        }
        return raw;
    }

}
