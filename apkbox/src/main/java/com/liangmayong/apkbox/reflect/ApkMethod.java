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
