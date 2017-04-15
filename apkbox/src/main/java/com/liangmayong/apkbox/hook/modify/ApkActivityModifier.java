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
package com.liangmayong.apkbox.hook.modify;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.reflect.ApkReflect;

/**
 * Created by LiangMaYong on 2016/9/21.
 */
public class ApkActivityModifier {

    private ApkActivityModifier() {
    }

    public static void modify(Activity target, String apkPath) {
        ApkLoaded loaded = ApkLoaded.get(target, apkPath);
        if (loaded != null) {
            modifyApplication(target, loaded);
            modifyTitle(target, loaded);
            modifyResources(target, loaded);
            modifyContext(target, loaded);
        }
    }

    private static void modifyApplication(Activity target, ApkLoaded loaded) {
        Application application = loaded.getApkApplication();
        if (application != null) {
            ApkReflect.setField(target.getClass(), target, "mApplication", application);
        }
    }

    private static Resources modifyResources(Activity target, ApkLoaded loaded) {
        Resources resources = loaded.getResources(target);
        if (resources != null) {
            ApkReflect.setField(target.getClass(), target, "mResources", resources);
        }
        return resources;
    }

    private static void modifyTitle(Activity target, ApkLoaded loaded) {
        try {
            target.setTitle(loaded.getApkName());
        } catch (Exception e) {
        }
    }

    private static Context modifyContext(Activity target, ApkLoaded loaded) {
        Context context = loaded.getContext(target.getBaseContext());
        if (context != null) {
            ApkReflect.setField(target.getClass(), target, "mBase", context);
        }
        return context;
    }

}
