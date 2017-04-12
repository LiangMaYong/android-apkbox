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
