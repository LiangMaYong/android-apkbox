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
package com.liangmayong.apkbox.core.loader;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import com.liangmayong.apkbox.R;

import java.util.Iterator;

/**
 * ApkProcess
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class ApkProcess {

    private ApkProcess() {
    }

    /**
     * getCurrentProcessName
     *
     * @param context
     * @return
     */
    public static String getCurrentProcessName(Context context) {
        int pid = Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        Iterator i$ = mActivityManager.getRunningAppProcesses().iterator();
        ActivityManager.RunningAppProcessInfo appProcess;
        do {
            if (!i$.hasNext()) {
                return null;
            }
            appProcess = (ActivityManager.RunningAppProcessInfo) i$.next();
        } while (appProcess.pid != pid);
        return appProcess.processName;
    }


    /**
     * validateProcessName
     *
     * @param context context
     * @param process process
     * @return bool
     */
    public static boolean validateProcessName(Context context, String process) {
        if (process.startsWith(":")) {
            process = context.getPackageName() + process;
        } else if ("".equals(process)) {
            process = context.getPackageName();
        }
        if (getCurrentProcessName(context).equals(process)) {
            return true;
        }
        return false;
    }

    public static String getApkProcessName(Context context) {
        return context.getString(R.string.apkbox_process);
    }

    public static boolean validateApkProcessName(Context context) {
        return validateProcessName(context,getApkProcessName(context));
    }
}
