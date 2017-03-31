package com.liangmayong.apkbox.core.resources;

import android.os.Build;

/**
 * ApkABI
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class ApkABI {

    private ApkABI() {
    }

    public static final String[] getWithABIs() {
        String[] abis = new String[]{};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            abis = Build.SUPPORTED_ABIS;
        } else {
            abis = new String[]{Build.CPU_ABI, Build.CPU_ABI2};
        }
        return abis;
    }

}
