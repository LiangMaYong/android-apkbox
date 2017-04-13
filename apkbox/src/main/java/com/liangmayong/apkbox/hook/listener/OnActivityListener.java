package com.liangmayong.apkbox.hook.listener;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by LiangMaYong on 2017/4/10.
 */
public interface OnActivityListener {

    void onPostCreate(Activity target, Bundle savedInstanceState, boolean after);

    void onCreate(Activity target, Bundle savedInstanceState, boolean after);

    void onStart(Activity target, boolean after);

    void onRestart(Activity target, boolean after);

    void onDestroy(Activity target, boolean after);

    void onPause(Activity target, boolean after);

    void onStop(Activity target, boolean after);

    void onResume(Activity target, boolean after);

}
