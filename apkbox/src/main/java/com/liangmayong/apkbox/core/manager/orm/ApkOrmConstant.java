package com.liangmayong.apkbox.core.manager.orm;

/**
 * Created by LiangMaYong on 2017/3/15.
 */
public interface ApkOrmConstant {

    int DB_VERSION = 1;
    String DB_NAME = "apkbox_list.db";
    String DB_TIMESTAMP = "timestamp";

    // apk
    String APK_TABLE = "apk";
    String APK_PATH = "a_path";
    String APK_PACKAGE_NAME = "a_package_name";
    String APK_VERSION_CODE = "a_version_code";
    String APK_VERSION_NAME = "a_version_name";
    String APK_DESCRIPTION = "a_description";
    String APK_SIGNTURE = "a_signture";
    String APK_SHA1 = "a_sha1";
    String APK_STATUS = "a_status";

}
