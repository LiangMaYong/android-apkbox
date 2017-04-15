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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LiangMaYong on 2017/3/15.
 */

public class ApkOrmHelper extends SQLiteOpenHelper {

    public ApkOrmHelper(Context context) {
        super(context, ApkOrmConstant.DB_NAME, null, ApkOrmConstant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // apk
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + ApkOrmConstant.APK_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ApkOrmConstant.APK_PATH + " TEXT NOT NULL,"
                + ApkOrmConstant.APK_PACKAGE_NAME + " TEXT NOT NULL,"
                + ApkOrmConstant.APK_VERSION_CODE + " INTEGER NOT NULL,"
                + ApkOrmConstant.APK_VERSION_NAME + " TEXT NOT NULL,"
                + ApkOrmConstant.APK_DESCRIPTION + " TEXT NOT NULL,"
                + ApkOrmConstant.APK_SIGNTURE + " TEXT NOT NULL,"
                + ApkOrmConstant.APK_SHA1 + " TEXT NOT NULL,"
                + ApkOrmConstant.APK_STATUS + " INTEGER NOT NULL,"
                + ApkOrmConstant.DB_TIMESTAMP + " INTEGER);");

        // apk index
        db.execSQL("CREATE INDEX IF NOT EXISTS APK_PATH_INDEX ON " + ApkOrmConstant.APK_TABLE + "(" + ApkOrmConstant.APK_PATH + ");");
        db.execSQL("CREATE INDEX IF NOT EXISTS APK_PACKAGE_NAME_INDEX ON " + ApkOrmConstant.APK_TABLE + "(" + ApkOrmConstant.APK_PACKAGE_NAME + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
