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

import com.liangmayong.apkbox.core.classloader.ApkClassLoader;
import com.liangmayong.apkbox.core.constant.ApkConstant;

/**
 * Created by LiangMaYong on 2017/4/5.
 */

public class HookActivity_New {

    private HookActivity_New() {
    }

    public static Activity onNewActivity(ClassLoader cl, String className, Intent intent) {
        if (intent.getComponent() != null) {
            if (intent.hasExtra(ApkConstant.EXTRA_APK_PATH)) {
                String apkPath = intent.getStringExtra(ApkConstant.EXTRA_APK_PATH);
                try {
                    ClassLoader classloader = ApkClassLoader.getClassloader(apkPath);
                    return (Activity) classloader.loadClass(className).newInstance();
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

}
