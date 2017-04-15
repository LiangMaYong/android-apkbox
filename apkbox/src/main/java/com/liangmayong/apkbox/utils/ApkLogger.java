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
