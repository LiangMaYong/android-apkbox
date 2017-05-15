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
package com.liangmayong.apkbox;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.core.manager.error.ApkException;
import com.liangmayong.apkbox.core.manager.install.ApkInstaller;
import com.liangmayong.apkbox.core.manager.orm.ApkOrmDao;
import com.liangmayong.apkbox.core.manager.orm.ApkOrmModel;
import com.liangmayong.apkbox.hook.ApkHook;
import com.liangmayong.apkbox.hook.listener.OnActivityListener;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LiangMaYong on 2017/3/29.
 */

public class ApkBox {

    private static volatile ApkBox ourInstance = null;

    public static ApkBox get() {
        if (ourInstance == null) {
            synchronized (ApkBox.class) {
                ourInstance = new ApkBox();
            }
        }
        return ourInstance;
    }


    private ExecutorService poolExecutor = null;

    private ApkBox() {
        poolExecutor = Executors.newFixedThreadPool(5);
    }

    private boolean isInitialized = false;

    /**
     * isInitialized
     *
     * @return isInitialized
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * initialize
     *
     * @param application application
     * @return true or false
     */
    public boolean initialize(Application application) {
        if (isInitialized()) {
            return true;
        }
        if (application == null) {
            return false;
        }
        ApkHook.hook(application);
        return true;
    }

    // activityListener
    private OnActivityListener activityListener;
    // parent classloader
    private ClassLoader supportClassLoader = null;

    public void setActivityListener(OnActivityListener listener) {
        this.activityListener = listener;
    }

    public OnActivityListener getActivityListener() {
        return activityListener;
    }

    public void setSupportClassLoader(ClassLoader supportClassLoader) {
        this.supportClassLoader = supportClassLoader;
    }

    public ClassLoader getSupportClassloader() {
        return this.supportClassLoader;
    }

    /////////////////////////////////////////////////////////////////////////////////////
    ////////// Manager
    /////////////////////////////////////////////////////////////////////////////////////

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj != null && msg.obj instanceof ListenerArgs) {
                ((ListenerArgs) msg.obj).onReturn();
            }
        }
    };

    private class ListenerArgs {
        private Listener listener;
        private Object loaded;
        private ApkException exception;
        private boolean isSuccess = false;

        public ListenerArgs(Listener listener, Object loaded, ApkException exception, boolean isSuccess) {
            this.listener = listener;
            this.loaded = loaded;
            this.exception = exception;
            this.isSuccess = isSuccess;
        }

        void onReturn() {
            if (listener == null) {
                return;
            }
            if (isSuccess) {
                listener.onSuccess(loaded);
            } else {
                listener.onFail(exception);
            }
        }
    }

    public void installApk(final Context context, final InputStream inputStream, final boolean force, final Listener listener) {
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ApkLoaded loaded = ApkInstaller.installApk(context, inputStream, force);
                    if (loaded != null) {
                        mHandler.obtainMessage(0, new ListenerArgs(listener, loaded, null, true)).sendToTarget();
                    } else {
                        mHandler.obtainMessage(0, new ListenerArgs(listener, null, new ApkException(), false)).sendToTarget();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.obtainMessage(0, new ListenerArgs(listener, null, new ApkException(), false)).sendToTarget();
                }
            }
        });
    }

    public void installApk(final Context context, final File apkFile, final boolean force, final Listener<ApkLoaded> listener) {
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ApkLoaded loaded = ApkInstaller.installApk(context, apkFile, force);
                    if (loaded != null) {
                        mHandler.obtainMessage(0, new ListenerArgs(listener, loaded, null, true)).sendToTarget();
                    } else {
                        mHandler.obtainMessage(0, new ListenerArgs(listener, null, new ApkException(), false)).sendToTarget();
                    }
                } catch (Exception e) {
                    mHandler.obtainMessage(0, new ListenerArgs(listener, null, new ApkException(), false)).sendToTarget();
                }
            }
        });
    }

    public void findApk(final Context context, final String pathOrPackageName, final Listener<ApkLoaded> listener) {
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ApkLoaded loaded = ApkInstaller.findApk(context, pathOrPackageName);
                    if (loaded != null) {
                        mHandler.obtainMessage(0, new ListenerArgs(listener, loaded, null, true)).sendToTarget();
                    } else {
                        mHandler.obtainMessage(0, new ListenerArgs(listener, null, new ApkException(), false)).sendToTarget();
                    }
                } catch (Exception e) {
                    mHandler.obtainMessage(0, new ListenerArgs(listener, null, new ApkException(), false)).sendToTarget();
                }
            }
        });
    }

    public void removeApk(final Context context, final String pathOrPackageName, final Listener<Integer> listener) {
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer count = ApkInstaller.removeApk(context, pathOrPackageName);
                    mHandler.obtainMessage(0, new ListenerArgs(listener, count, null, true)).sendToTarget();
                } catch (Exception e) {
                    mHandler.obtainMessage(0, new ListenerArgs(listener, null, new ApkException(), false)).sendToTarget();
                }
            }
        });
    }

    public void clearApk(final Context context, final Listener<Integer> listener) {
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer count = ApkInstaller.clearApk(context);
                    mHandler.obtainMessage(0, new ListenerArgs(listener, count, null, true)).sendToTarget();
                } catch (Exception e) {
                    mHandler.obtainMessage(0, new ListenerArgs(listener, null, new ApkException(), false)).sendToTarget();
                }
            }
        });
    }

    public ApkOrmModel findApkModel(final Context context, final String pathOrPackageName) {
        return ApkOrmDao.get().findApk(context, pathOrPackageName);
    }

    public List<ApkOrmModel> findApkModelList(final Context context) {
        return ApkOrmDao.get().getApkList(context);
    }

    public interface Listener<T> {

        void onSuccess(T t);

        void onFail(ApkException exception);

    }
}
