package com.liangmayong.apkbox.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.liangmayong.apkbox.activity.proxy.ProxyActivity;
import com.liangmayong.apkbox.core.context.ApkContext;

/**
 * Created by liangmayong on 2017/3/31.
 */

public class ApkActivityLauncher {

    private ApkActivityLauncher() {
    }


    /**
     * startActivity
     *
     * @param context activity
     * @param path    path
     * @param actName actName
     */
    public static void startActivity(Context context, String path, String actName) {
        startActivity(context, path, actName, null);
    }

    /**
     * startActivity
     *
     * @param context activity
     * @param path    path
     * @param actName actName
     * @param extars  extars
     */
    public static void startActivity(Context context, String path, String actName, Bundle extars) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ProxyActivity.class);
        intent.putExtra(ApkActivityConstant.EXTRA_APK_PATH, path);
        intent.putExtra(ApkActivityConstant.EXTRA_APK_ACTIVITY, actName);
        if (extars != null) {
            intent.putExtras(extars);
        }
        context.startActivity(intent);
    }

    /**
     * startActivityForResult
     *
     * @param activity    activity
     * @param requestCode requestCode
     * @param path        path
     * @param actName     actName
     */
    public static void startActivityForResult(Activity activity, int requestCode, String path, String actName) {
        startActivityForResult(activity, requestCode, path, actName, null);
    }

    /**
     * startActivityForResult
     *
     * @param activity    activity
     * @param requestCode requestCode
     * @param path        path
     * @param actName     actName
     * @param extars      extars
     */
    public static void startActivityForResult(Activity activity, int requestCode, String path, String actName, Bundle extars) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(ApkContext.get(activity, path), actName);
        intent.putExtra(ApkActivityConstant.EXTRA_APK_PATH, path);
        intent.putExtra(ApkActivityConstant.EXTRA_APK_ACTIVITY, actName);
        if (extars != null) {
            intent.putExtras(extars);
        }
        activity.startActivityForResult(intent, requestCode);
    }

}
