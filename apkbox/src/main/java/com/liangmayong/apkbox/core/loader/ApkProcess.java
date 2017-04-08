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
