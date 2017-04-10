package com.liangmayong.apkbox.hook.listener;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by LiangMaYong on 2017/4/10.
 */
public interface OnActivityListener {

    void onPostCreate(Activity target, Bundle savedInstanceState);

    void onCreate(Activity target, Bundle savedInstanceState);

    void onStart(Activity target);

    void onRestart(Activity target);

    void onDestroy(Activity target);

    void onPause(Activity target);

    void onStop(Activity target);

    void onResume(Activity target);

}
