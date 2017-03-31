package com.liangmayong.apkbox.core.loader;

import android.content.res.AssetManager;

import com.liangmayong.apkbox.core.loader.ApkXmlParser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiangMaYong on 2017/3/30.
 */

public class ApkConfigure {

    private ApkConfigure() {
    }

    public static Map<String, String> getConfigure(AssetManager assetManager) {
        try {
            InputStream inputStream = assetManager.open("apkbox.xml");
            List<Map<String, String>> mapLists = ApkXmlParser.readXml(inputStream, "apkbox");
            if (mapLists != null && !mapLists.isEmpty()) {
                return mapLists.get(0);
            }
        } catch (Exception e) {
        }
        return new HashMap<String, String>();
    }
}
