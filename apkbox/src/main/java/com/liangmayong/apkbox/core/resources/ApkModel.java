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
