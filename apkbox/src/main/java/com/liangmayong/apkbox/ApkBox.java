package com.liangmayong.apkbox;

import android.app.Application;

import com.liangmayong.apkbox.hook.ApkHook;

/**
 * Created by LiangMaYong on 2017/3/29.
 */

public class ApkBox {

    private static volatile ApkBox ourInstance = null;

    public static ApkBox get() {
        if (ourInstance == null) {
            synchronized (ApkBox.class) {
                ourInstance = new ApkBox();
            }
        }
        return ourInstance;
    }

    private ApkBox() {
    }

    private boolean isInitialized = false;

    /**
     * isInitialized
     *
     * @return isInitialized
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * initialize
     *
     * @param application application
     * @return true or false
     */
    public boolean initialize(Application application) {
        if (isInitialized()) {
            return true;
        }
        if (application == null) {
            return false;
        }
        ApkHook.hook(application);
        return true;
    }


    /**
     * parent classloader
     */
    private ClassLoader supportClassLoader = null;

    /**
     * setSupportClassLoader
     *
     * @param supportClassLoader supportClassLoader
     */
    public void setSupportClassLoader(ClassLoader supportClassLoader) {
        this.supportClassLoader = supportClassLoader;
    }

    /**
     * getSupportClassloader
     *
     * @return supportClassLoader
     */
    public ClassLoader getSupportClassloader() {
        return this.supportClassLoader;
    }

}
