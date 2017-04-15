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
package com.liangmayong.apkbox.hook;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.liangmayong.apkbox.hook.activity.HookActivity_LaunchActivity;
import com.liangmayong.apkbox.reflect.ApkReflect;

/**
 * Created by LiangMaYong on 2016/9/19.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public final class HookActivityThreadHandlerCallback implements Handler.Callback {

    private final Handler mBase;
    private int LAUNCH_ACTIVITY = 100;

    public HookActivityThreadHandlerCallback(Handler base) {
        mBase = base;
        try {
            LAUNCH_ACTIVITY = (Integer) ApkReflect.getField(Class.forName("android.app.ActivityThread$H"), null, "LAUNCH_ACTIVITY");
        } catch (Exception e) {
        }
    }

    @Override
    public boolean handleMessage(final Message msg) {
        if (msg.what == LAUNCH_ACTIVITY) {
            // LAUNCH_ACTIVITY
            HookActivity_LaunchActivity.handleLaunchActivity(msg);
        }
        mBase.handleMessage(msg);
        return true;
    }
}
