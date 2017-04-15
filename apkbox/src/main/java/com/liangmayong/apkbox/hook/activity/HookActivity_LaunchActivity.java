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

import android.content.Intent;
import android.os.Message;

import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.utils.ApkBuild;
import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Field;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookActivity_LaunchActivity {

    private static final boolean DEBUG_HOOK_PROXY = ApkBuild.DEBUG_HOOK_PROXY;

    private HookActivity_LaunchActivity() {
    }

    public static void handleLaunchActivity(Message msg) {
        if (DEBUG_HOOK_PROXY)
            ApkLogger.get().debug("hook handleLaunchActivity", null);
        Object obj = msg.obj;
        try {
            Field intent = obj.getClass().getDeclaredField("intent");
            intent.setAccessible(true);
            Intent raw = (Intent) intent.get(obj);
            if (raw.hasExtra(ApkConstant.EXTRA_APK_TARGET_INTENT)) {
                Intent target = raw.getParcelableExtra(ApkConstant.EXTRA_APK_TARGET_INTENT);
                intent.set(obj, target);
            }
        } catch (Exception e) {
        }
    }

}
