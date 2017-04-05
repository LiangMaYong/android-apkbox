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
