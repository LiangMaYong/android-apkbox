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

import android.view.LayoutInflater;
import android.view.View;

import com.liangmayong.apkbox.reflect.ApkReflect;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * Created by LiangMaYong on 2016/9/21.
 */
public class ApkLayoutInflaterModifier {

    private ApkLayoutInflaterModifier() {
    }

    private static String cusApkPath = "";
    static HashMap<String, Constructor<? extends View>> sConstructorMap = null;

    static {
        try {
            sConstructorMap = (HashMap<String, Constructor<? extends View>>) ApkReflect.getField(LayoutInflater.class, null, "sConstructorMap");
        } catch (Throwable e) {
        }
    }

    public static void modify(String apkPath) {
        if (apkPath == null || apkPath.equals(cusApkPath)) {
            return;
        } else {
            cusApkPath = apkPath;
            if (sConstructorMap != null) {
                sConstructorMap.clear();
            }
        }
    }
}
