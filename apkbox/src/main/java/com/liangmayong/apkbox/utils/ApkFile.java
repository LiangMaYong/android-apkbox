package com.liangmayong.apkbox.utils;

import java.io.File;

/**
 * Created by LiangMaYong on 2017/4/12.
 */
public class ApkFile {

    private ApkFile() {
    }

    public static void deleteFile(File file) {
        if (file.exists() == false) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    deleteFile(f);
                }
                file.delete();
            }
        }
    }

    public static String md5(String str) {
        return "";
    }

}
