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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by LiangMaYong on 2017/4/12.
 */
public class ApkOrmDao extends AbstractDao {

    private static volatile ApkOrmDao ourInstance = null;

    public static ApkOrmDao get() {
        if (ourInstance == null) {
            synchronized (ApkOrmDao.class) {
                ourInstance = new ApkOrmDao();
            }
        }
        return ourInstance;
    }

    private ApkOrmDao() {
    }

    private Converter<ApkOrmModel, Cursor> cursorConverter = new Converter<ApkOrmModel, Cursor>() {
        @Override
        public ApkOrmModel convert(Cursor cursor) {
            ApkOrmModel model = new ApkOrmModel();
            model.setId(readLong("_id", cursor));
            model.setApkPath(readString(ApkOrmConstant.APK_PATH, cursor));
            model.setApkPackageName(readString(ApkOrmConstant.APK_PACKAGE_NAME, cursor));
            model.setApkVersionCode(readInt(ApkOrmConstant.APK_VERSION_CODE, cursor));
            model.setApkVersionName(readString(ApkOrmConstant.APK_VERSION_NAME, cursor));
            model.setApkDescription(readString(ApkOrmConstant.APK_DESCRIPTION, cursor));
            model.setApkSignture(readString(ApkOrmConstant.APK_SIGNTURE, cursor));
            model.setApkSha1(readString(ApkOrmConstant.APK_SHA1, cursor));
            model.setApkStatus(readInt(ApkOrmConstant.APK_STATUS, cursor));
            return model;
        }
    };

    private Converter<ContentValues, ApkOrmModel> modelConverter = new Converter<ContentValues, ApkOrmModel>() {
        @Override
        public ContentValues convert(ApkOrmModel model) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ApkOrmConstant.APK_PATH, model.getApkPath());
            contentValues.put(ApkOrmConstant.APK_PACKAGE_NAME, model.getApkPackageName());
            contentValues.put(ApkOrmConstant.APK_VERSION_CODE, model.getApkVersionCode());
            contentValues.put(ApkOrmConstant.APK_VERSION_NAME, model.getApkVersionName());
            contentValues.put(ApkOrmConstant.APK_DESCRIPTION, model.getApkDescription());
            contentValues.put(ApkOrmConstant.APK_SIGNTURE, model.getApkSignture());
            contentValues.put(ApkOrmConstant.APK_SHA1, model.getApkSha1());
            contentValues.put(ApkOrmConstant.APK_STATUS, model.getApkStatus());
            contentValues.put(ApkOrmConstant.DB_TIMESTAMP, System.currentTimeMillis());
            return contentValues;
        }
    };

    @Override
    protected SQLiteOpenHelper getHelper(Context context) {
        return new ApkOrmHelper(context);
    }

    /**
     * installApk
     *
     * @param context context
     * @param model   model
     * @return id
     */
    public long installApk(Context context, ApkOrmModel model) {
        if (model == null) {
            return 0;
        }
        if (hasApk(context, model)) {
            return 0;
        }
        long id = insertData(context, ApkOrmConstant.APK_TABLE, model, modelConverter);
        model.setId(id);
        return id;
    }

    /**
     * findApk
     *
     * @param context           context
     * @param pathOrPackageName pathOrPackageName
     * @return apk model
     */
    public ApkOrmModel findApk(Context context, String pathOrPackageName) {
        String where = ApkOrmConstant.APK_PATH + " = '" + pathOrPackageName + "'";
        ApkOrmModel model = getData(context, ApkOrmConstant.APK_TABLE, where, null, cursorConverter);
        if (model == null) {
            where = ApkOrmConstant.APK_PACKAGE_NAME + " = '" + pathOrPackageName + "'";
            model = getData(context, ApkOrmConstant.APK_TABLE, where, null, cursorConverter);
        }
        return model;
    }

    /**
     * getApkList
     *
     * @param context context
     * @return list
     */
    public List<ApkOrmModel> getApkList(Context context) {
        return getDataList(context, ApkOrmConstant.APK_TABLE, null, null, null, cursorConverter);
    }

    /**
     * removeApk
     *
     * @param context context
     * @param model   model
     * @return count
     */
    public int removeApk(Context context, ApkOrmModel model) {
        return deleteData(context, ApkOrmConstant.APK_TABLE, ApkOrmConstant.APK_PATH + " = '" + model.getApkPackageName() + "'");
    }

    /**
     * hasApk
     *
     * @param context context
     * @param model   model
     * @return bool
     */
    public boolean hasApk(Context context, ApkOrmModel model) {
        return hasData(context, ApkOrmConstant.APK_TABLE, ApkOrmConstant.APK_PATH + " = '" + model.getApkPackageName() + "'");
    }
}
