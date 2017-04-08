package com.liangmayong.apkbox.proxy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by LiangMaYong on 2017/3/29.
 */
public class Proxy404Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }

}
