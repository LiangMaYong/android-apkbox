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
package com.liangmayong.apkbox.core.context;

import android.content.Context;

import java.lang.reflect.Method;

/**
 * Created by LiangMaYong on 2016/9/21.
 */
public class ApkContextModifier {
    private static Class<?> CONTEXT_IMPL_CLASS = null;
    private static Method m_setOuterContext = null;

    static {
        try {
            CONTEXT_IMPL_CLASS = Class.forName("android.app.ContextImpl");
        } catch (ClassNotFoundException e) {
            // Ignore
        }
        try {
            m_setOuterContext = CONTEXT_IMPL_CLASS.getDeclaredMethod("setOuterContext", Context.class);
            if (!m_setOuterContext.isAccessible()) {
                m_setOuterContext.setAccessible(true);
            }
        } catch (Throwable e) {
            // Ignore
        }
    }

    public static void setOuterContext(Context contextImpl, Context outerContext) {
        try {
            m_setOuterContext.invoke(contextImpl, outerContext);
        } catch (Throwable e) {
        }
    }
}
