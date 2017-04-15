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
package com.liangmayong.apkbox.reflect;

import java.lang.reflect.Method;

/**
 * Created by liangmayong on 2016/9/18.
 */
public final class ApkMethod {

    private static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        Method method = null;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(name, parameterTypes);
                return method;
            } catch (Exception e) {
            }
        }
        return null;
    }

    private Method method = null;
    private Object object = null;

    public ApkMethod(Class<?> cls, Object object, String method, Class<?>... parameterTypes) {
        try {
            this.object = object;
            this.method = getMethod(cls, method, parameterTypes);
        } catch (Exception e) {
        }
    }

    public <T> T invoke(Object... args) throws Exception {
        if (method != null) {
            method.setAccessible(true);
            Object object = method.invoke(this.object, args);
            method = null;
            this.object = null;
            return (T) object;
        }
        return null;
    }
}
