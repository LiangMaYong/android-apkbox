package com.liangmayong.apkbox.activity.proxy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by LiangMaYong on 2017/3/29.
 */
public class ProxyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "ProxyActivity", Toast.LENGTH_SHORT).show();
    }
}
