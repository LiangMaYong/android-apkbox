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

import android.app.Application;

import com.liangmayong.apkbox.reflect.ApkReflect;

import java.lang.reflect.Method;

/**
 * Created by liangmayong on 2017/4/8.
 */
public class HookCurrentActivityThread {

    private static Object currentActivityThread = null;
    private static Class<?> activityThreadClass = null;

    public static Object getCurrentActivityThread(Application application) {
        if (currentActivityThread == null) {
            Object loadedApk = ApkReflect.getField(Application.class, application, "mLoadedApk");
            currentActivityThread = ApkReflect.getField(loadedApk.getClass(), loadedApk, "mActivityThread");
        }
        if (currentActivityThread == null) {
            try {
                Method currentActivityThreadMethod = getActivityThreadClass().getDeclaredMethod("currentActivityThread");
                currentActivityThread = currentActivityThreadMethod.invoke(null);
            } catch (Exception e) {
            }
        }
        if (currentActivityThread == null) {
            try {
                currentActivityThread = ApkReflect.getField(getActivityThreadClass(), null, "sCurrentActivityThread");
            } catch (Exception e) {
            }
        }
        return currentActivityThread;
    }

    public static Class<?> getActivityThreadClass() {
        if (activityThreadClass == null) {
            try {
                activityThreadClass = Class.forName("android.app.ActivityThread");
            } catch (Exception e) {
            }
        }
        return activityThreadClass;
    }
}
