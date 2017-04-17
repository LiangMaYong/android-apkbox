package com.liangmayong.android_apkbox;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangmayong.apkbox.ApkBox;
import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.core.manager.error.ApkException;

import java.io.IOException;

public class MainActivity extends Activity {

    private String appName = "PLPlayer.apk";
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        installApk();
    }

    public void installApk() {
        try {
            ApkBox.get().installApk(MainActivity.this, getAssets().open("plugins/" + appName), false, new ApkBox.Listener<ApkLoaded>() {
                @Override
                public void onSuccess(final ApkLoaded loaded) {
                    Log.e("TAG", loaded + "");
                    imageView.setImageDrawable(loaded.getApkIcon());
                    textView.setText(loaded.getApkName());
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (loaded != null) {
                                loaded.launch(MainActivity.this, null);
                            }
                        }
                    });
                }

                @Override
                public void onFail(ApkException exception) {
                    exception.printStackTrace();

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
    }
}
