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

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangmayong on 2016/9/18.
 */
public final class ApkResources extends Resources {

    // ASSETS
    private static final Map<String, AssetManager> ASSETS = new HashMap<String, AssetManager>();
    // RESOURCES
    private static final Map<String, ApkResources> RESOURCES = new HashMap<String, ApkResources>();
    // addAssetPathMethod
    private static Method addAssetPathMethod = null;

    public static AssetManager getAssets(Context context, String apkPath) {
        String key = apkPath;
        if (ASSETS.containsKey(key)) {
            return ASSETS.get(key);
        }
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            if (addAssetPathMethod == null) {
                addAssetPathMethod = AssetManager.class.getMethod("addAssetPath", String.class);
                addAssetPathMethod.setAccessible(true);
            }
            addAssetPathMethod.invoke(assetManager, apkPath);
            ASSETS.put(key, assetManager);
            return assetManager;
        } catch (Throwable th) {
        }
        return context.getAssets();
    }

    /**
     * getResources
     *
     * @param apkPath apkPath
     * @return resources
     */
    public static Resources getResources(Context context, String apkPath) {
        if (apkPath == null || "".equals(apkPath)) {
            return context.getResources();
        }
        String key = apkPath;
        if (RESOURCES.containsKey(key)) {
            return RESOURCES.get(key);
        }
        AssetManager assetManager = getAssets(context, apkPath);
        ApkResources apkResources = new ApkResources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration(), apkPath, context.getResources());
        RESOURCES.put(key, apkResources);
        return apkResources;
    }

    private String apkPath = "";
    private Resources hostResources = null;

    /**
     * ApkResources
     *
     * @param assets  assets
     * @param metrics metrics
     * @param config  config
     */
    private ApkResources(AssetManager assets, DisplayMetrics metrics, Configuration config, String apkPath, Resources resources) {
        super(assets, metrics, config);
        this.apkPath = apkPath;
        this.hostResources = resources;
    }

    public Resources getHostResources() {
        return hostResources;
    }

    public String getApkPath() {
        return apkPath;
    }
}
