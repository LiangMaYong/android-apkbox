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
package com.liangmayong.apkbox.core.manager.install;

import android.content.Context;

import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.core.loader.ApkLoader;
import com.liangmayong.apkbox.core.manager.orm.ApkOrmDao;
import com.liangmayong.apkbox.core.manager.orm.ApkOrmModel;
import com.liangmayong.apkbox.core.resources.ApkNative;
import com.liangmayong.apkbox.utils.ApkFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by LiangMaYong on 2017/4/12.
 */
public class ApkInstaller {

    private ApkInstaller() {
    }

    public static ApkLoaded installApk(Context context, InputStream inputStream, boolean force) throws Exception {
        File file = copyApk(context, inputStream);
        return doInstallApk(context, file, force);
    }

    public static ApkLoaded installApk(Context context, File apkFile, boolean force) throws Exception {
        File file = copyApk(context, new FileInputStream(apkFile));
        return doInstallApk(context, file, force);
    }

    public static ApkLoaded findApk(Context context, String pathOrPackageName) throws Exception {
        ApkOrmModel model = ApkOrmDao.get().findApk(context, pathOrPackageName);
        if (model != null) {
            return ApkLoaded.get(context, model.getApkPath());
        }
        return null;
    }

    public static int removeApk(Context context, String pathOrPackageName) throws Exception {
        ApkOrmModel model = ApkOrmDao.get().findApk(context, pathOrPackageName);
        if (model != null) {
            ApkFile.deleteFile(new File(model.getApkPath()));
            ApkNative.clearNativeLibrary(model.getApkPath());
            return ApkOrmDao.get().removeApk(context, model);
        }
        return 0;
    }

    public static int clearApk(Context context) throws Exception {
        List<ApkOrmModel> list = ApkOrmDao.get().getApkList(context);
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            count += removeApk(context, list.get(i).getApkPackageName());
        }
        return count;
    }

    private static ApkLoaded doInstallApk(Context context, File apkFile, boolean force) throws Exception {
        ApkOrmModel apkModel = ApkLoader.parserApkOrmModel(context, apkFile.getPath());
        if (apkModel != null) {
            boolean install = true;
            ApkOrmModel model = ApkOrmDao.get().findApk(context, apkModel.getApkPackageName());
            String oldPath = "";
            if (model != null) {
                oldPath = model.getApkPath();
                if (apkModel.getApkSha1().equals(model.getApkSha1())) {
                    install = false;
                }
            }
            if (install || force) {
                ApkLoaded rawLoaded = ApkLoaded.get(context, apkFile.getPath());
                if (rawLoaded != null) {
                    if (!"".equals(oldPath)) {
                        ApkFile.deleteFile(new File(oldPath));
                        ApkNative.clearNativeLibrary(oldPath);
                    }
                    if (model != null) {
                        ApkOrmDao.get().removeApk(context, model);
                    }
                    ApkOrmDao.get().installApk(context, apkModel);
                    return rawLoaded;
                }
            }
            ApkFile.deleteFile(apkFile);
            if (model != null) {
                return ApkLoaded.get(context, model.getApkPath());
            }
        }
        throw new Exception("parserApkOrmModel Fail");
    }


    private static File getApkDir(Context context) {
        File apkDir = context.getDir("apkbox", Context.MODE_PRIVATE);
        if (!apkDir.exists()) {
            apkDir.mkdirs();
        }
        return apkDir;
    }

    private static File copyApk(Context context, InputStream inputStream) {
        try {
            File file = new File(getApkDir(context), System.currentTimeMillis() + "_" + UUID.randomUUID() + ".apk");
            OutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[4096];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            inputStream.close();
            inputStream = null;
            out.flush();
            out.close();
            out = null;
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
