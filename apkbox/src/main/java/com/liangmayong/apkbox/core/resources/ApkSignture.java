package com.liangmayong.apkbox.core.resources;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.security.MessageDigest;

/**
 * ApkSignture
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class ApkSignture {

    /**
     * getApkSignture
     *
     * @param context context
     * @param appPath appPath
     * @return signture
     */
    @SuppressLint("DefaultLocale")
    public static String getSignture(Context context, String appPath) {
        String signature = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageArchiveInfo(appPath, PackageManager.GET_SIGNATURES);
            Signature[] signatures = pi.signatures;
            if (signatures != null && signatures.length > 0) {
                String sign = signatures[0].toCharsString();
                try {
                    MessageDigest localMessageDigest;
                    localMessageDigest = MessageDigest.getInstance("MD5");
                    localMessageDigest.update(sign.getBytes());
                    String md5 = toHex(localMessageDigest.digest()).toUpperCase();
                    int count = md5.length();
                    for (int i = 0; i < count; i += 2) {
                        if ((i % 2) == 0) {
                            signature += md5.substring(i, i + 2);
                            if (i != count - 2) {
                                signature += ":";
                            }
                        }
                    }
                } catch (Exception e) {
                    signature = sign;
                }
                return signature;
            }
        } catch (Exception e) {
        }
        return signature;
    }

    /**
     * toHex
     *
     * @param bytes bytes
     * @return hex string
     */
    private static String toHex(byte[] bytes) {
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Byte.valueOf(bytes[i]);
            localStringBuffer.append(String.format("%02x", arrayOfObject));
        }
        return localStringBuffer.toString();
    }
}
