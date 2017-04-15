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
