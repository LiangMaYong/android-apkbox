package com.liangmayong.apkbox.core.classloader;

import com.liangmayong.apkbox.ApkBox;

/**
 * Created by LiangMaYong on 2017/3/30.
 */
public class ApkDexClassLoader extends ClassLoader {

    private ClassLoader mClassLoader;

    public ApkDexClassLoader(ClassLoader classLoader) {
        this.mClassLoader = classLoader;
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        Class<?> clazz = findLoadedClass(className);
        if (clazz != null) {
            return clazz;
        }
        if (ApkBox.get().getSupportClassloader() != null) {
            try {
                clazz = ApkBox.get().getSupportClassloader().loadClass(className);
                if (clazz != null) {
                    return clazz;
                }
            } catch (Exception e) {
            }
        }
        Exception exception = null;
        try {
            clazz = mClassLoader.loadClass(className);
            if (clazz != null) {
                return clazz;
            }
        } catch (Exception e) {
            exception = e;
        }
        try {
            return super.loadClass(className);
        } catch (Exception e) {
        }
        throw new ClassNotFoundException("not found class " + className, exception);
    }

}
