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
import android.os.Bundle;
import android.util.Log;

import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.core.resources.ApkExtras;
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
