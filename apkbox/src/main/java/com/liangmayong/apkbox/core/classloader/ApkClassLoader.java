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
        return loadClassloader(apkPath, false);
    }

    /**
     * loadClassloader
     *
     * @param apkPath apkPath
     */
    public static ClassLoader loadClassloader(String apkPath, boolean force) {
        ClassLoader classLoader = null;
        if (apkPath == null || "".equals(apkPath) || !(new File(apkPath).exists())) {
            classLoader = ApkClassLoader.class.getClassLoader();
        } else {
            String key = apkPath;
            if (!force && CLASSLOADERS.containsKey(key)) {
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
