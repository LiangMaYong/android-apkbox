package com.liangmayong.apkbox.core.model;

import com.liangmayong.apkbox.core.classloader.ApkClassLoader;
import com.liangmayong.apkbox.reflect.ApkReflect;

/**
 * ApkModelBuilder
 *
 * @param <T> type
 * @author LiangMaYong
 * @version 1.0
 */
public final class ApkModelBuilder<T> {

    private Class<T> clazz;

    public ApkModelBuilder(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * getModel
     *
     * @param appPath   appPath
     * @param className className
     * @return t
     */
    public T getModel(String appPath, String className) {
        try {
            Class<?> c = null;
            c = ApkClassLoader.loadClass(appPath, className);
            Object model = c.newInstance();
            T newModel = clazz.newInstance();
            ApkReflect.cloneModel(model, newModel);
            return newModel;
        } catch (Exception e) {
        }
        return null;
    }

}
