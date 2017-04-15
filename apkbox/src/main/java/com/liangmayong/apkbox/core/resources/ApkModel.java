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
package com.liangmayong.apkbox.core.resources;

import com.liangmayong.apkbox.core.classloader.ApkClassLoader;
import com.liangmayong.apkbox.reflect.ApkReflect;

/**
 * ApkModel
 *
 * @param <T> type
 * @author LiangMaYong
 * @version 1.0
 */
public final class ApkModel<T> {

    private Class<T> clazz;

    public ApkModel(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * getModel
     *
     * @param apkPath   appPath
     * @param className className
     * @return t
     */
    public T getModel(String apkPath, String className) {
        try {
            Class<?> c = null;
            c = ApkClassLoader.loadClass(apkPath, className);
            Object model = c.newInstance();
            T newModel = clazz.newInstance();
            ApkReflect.cloneModel(model, newModel);
            return newModel;
        } catch (Exception e) {
        }
        return null;
    }

}
