/**
 The MIT License (MIT)

 Copyright (c) 2017 LiangMaYong ( ibeam@qq.com )

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/ or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 **/
package com.liangmayong.apkbox.hook.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.core.resources.ApkExtras;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookActivity_Intent {

    private HookActivity_Intent() {
    }

    public static Intent modify(Activity target, Intent intent) {
        if (!intent.hasExtra(ApkConstant.EXTRA_APK_PATH)
                && target.getIntent().hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                intent.replaceExtras(new Bundle());
                String extras_id = ApkExtras.putExtras(extras);
                intent.putExtra(ApkConstant.EXTRA_APK_EXTRAS, extras_id);
            }
            String apkPath = target.getIntent().getStringExtra(ApkConstant.EXTRA_APK_PATH);
            intent.putExtra(ApkConstant.EXTRA_APK_PATH, apkPath);
            // launch mode
            if (intent.getComponent() != null) {
                try {
                    ActivityInfo activityInfo = ApkLoaded.get(target, apkPath).getActivityInfo(intent.getComponent().getClassName());
                    String launchModeStr = HookActivity_Component.STANDARD;
                    int launchMode = activityInfo.launchMode;
                    if (launchMode == ActivityInfo.LAUNCH_SINGLE_INSTANCE) {
                        launchModeStr = HookActivity_Component.SINGLE_INSTANCE;
                    } else if (launchMode == ActivityInfo.LAUNCH_SINGLE_TASK) {
                        launchModeStr = HookActivity_Component.SINGLE_TASK;
                    } else if (launchMode == ActivityInfo.LAUNCH_SINGLE_TOP) {
                        launchModeStr = HookActivity_Component.SINGLE_TOP;
                    }
                    intent.putExtra(ApkConstant.EXTRA_APK_LAUNCH_MODE, launchModeStr);
                } catch (Exception e) {
                }
            }
        }
        return HookActivity_Component.modify(intent);
    }

}
