package com.liangmayong.android_apkbox;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liangmayong.apkbox.core.ApkLoaded;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends Activity {

    private ApkLoaded loaded = null;
    private String appName = "PLPlayer.apk";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loaded = (ApkLoaded) msg.obj;
            if (loaded == null) {
                Toast.makeText(MainActivity.this, "loaded is null", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.e("TAG", loaded + "");
            imageView.setImageDrawable(loaded.getApkIcon());
            textView.setText(loaded.getApkName());
        }
    };
    private ImageView imageView;
    private RelativeLayout activity_main;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _initView();
        install();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loaded != null) {
                    loaded.launch(MainActivity.this, null);
                }
            }
        });
    }


    public void install() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream out = null;
                try {
                    InputStream inputStream = getAssets().open("plugins/" + appName);
                    File dexTemp = getDir("apkbox", Context.MODE_PRIVATE);
                    dexTemp.mkdirs();
                    File pluginTemp = new File(dexTemp, appName);
                    out = new FileOutputStream(pluginTemp);
                    byte[] buffer = new byte[4096];
                    int read;
                    while ((read = inputStream.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                    inputStream.close();
                    inputStream = null;
                    out.flush();
                    out.close();
                    out = null;
                    loaded = ApkLoaded.get(MainActivity.this, pluginTemp.getPath());
                    Message message = new Message();
                    message.what = 1;
                    message.obj = loaded;
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void _initView() {
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);
    }
}
