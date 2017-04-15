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
package com.liangmayong.apkbox.core.classloader;

import com.liangmayong.apkbox.core.resources.ApkNative;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * Created by liangmayong on 2016/9/18.
 */
public final class ApkClassLoader {

    private ApkClassLoader() {
    }

    /**
     * CLASSLOADERS
     */
    private static final Map<String, ClassLoader> CLASSLOADERS = new HashMap<String, ClassLoader>();

    /**
     * loadClass
     *
     * @param apkPath   apkPath
     * @param className className
     * @return clazz
     */
    public static Class<?> loadClass(String apkPath, String className) {
        try {
            return getClassloader(apkPath).loadClass(className);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * getClassloader
     *
     * @param apkPath apkPath
     * @return classloader
     */
    public static ClassLoader getClassloader(String apkPath) {
        ClassLoader classLoader = null;
        if (apkPath == null || "".equals(apkPath) || !(new File(apkPath).exists())) {
            classLoader = ApkClassLoader.class.getClassLoader();
        } else {
            String key = apkPath;
            if (CLASSLOADERS.containsKey(key)) {
                classLoader = CLASSLOADERS.get(key);
            } else {
                classLoader = newApkDexLoader(apkPath);
                CLASSLOADERS.put(key, classLoader);
            }
        }
        return classLoader;
    }

    /**
     * newApkDexLoader
     *
     * @param apkPath apkPath
     * @return ClassLoader
     */
    private static ClassLoader newApkDexLoader(String apkPath) {
        try {
            ClassLoader dexClassLoader = new DexClassLoader(apkPath, new File(apkPath).getParent(),
                    ApkNative.getNativePath(apkPath), ClassLoader.getSystemClassLoader());
            return new ApkDexClassLoader(dexClassLoader);
        } catch (Exception e) {
            return null;
        }
    }

}
