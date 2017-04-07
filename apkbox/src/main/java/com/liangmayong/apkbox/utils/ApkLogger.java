package com.liangmayong.apkbox.utils;

import android.util.Log;

import com.liangmayong.apkbox.reflect.ApkReflect;

/**
 * Created by LiangMaYong on 2017/3/31.
 */
public class ApkLogger {

    private static volatile ApkLogger ourInstance = null;

    public static ApkLogger get() {
        if (ourInstance == null) {
            synchronized (ApkLogger.class) {
                ourInstance = new ApkLogger();
            }
        }
        return ourInstance;
    }

    public interface OnApkLoggerListener {

        String getTag();

        void debug(String tag, String log, Throwable throwable);

        void error(String tag, String log, Throwable throwable);

        void info(String tag, String log, Throwable throwable);

        void warn(String tag, String log, Throwable throwable);
    }

    private ApkLogger() {
    }

    private boolean debug = true;
    private OnApkLoggerListener loggerListener = new OnApkLoggerListener() {
        @Override
        public String getTag() {
            return "ApkLogger";
        }

        @Override
        public void debug(String tag, String log, Throwable throwable) {
            Log.d(tag, log, throwable);
        }

        @Override
        public void error(String tag, String log, Throwable throwable) {
            Log.e(tag, log, throwable);
        }

        @Override
        public void info(String tag, String log, Throwable throwable) {
            Log.i(tag, log, throwable);
        }

        @Override
        public void warn(String tag, String log, Throwable throwable) {
            Log.w(tag, log, throwable);
        }
    };

    public void setLoggerListener(OnApkLoggerListener loggerListener) {
        this.loggerListener = loggerListener;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void debug(String log, Throwable throwable) {
        if (debug && loggerListener != null) {
            loggerListener.debug(loggerListener.getTag(), log, throwable);
        }
    }

    public void error(String log, Throwable throwable) {
        if (debug && loggerListener != null) {
            loggerListener.error(loggerListener.getTag(), log, throwable);
        }
    }

    public void info(String log, Throwable throwable) {
        if (debug && loggerListener != null) {
            loggerListener.info(loggerListener.getTag(), log, throwable);
        }
    }

    public void warn(String log, Throwable throwable) {
        if (debug && loggerListener != null) {
            loggerListener.warn(loggerListener.getTag(), log, throwable);
        }
    }

    public void print(String log, Object object) {
        if (debug) {
            debug(log, null);
            ApkReflect.print(object);
        }
    }

}
