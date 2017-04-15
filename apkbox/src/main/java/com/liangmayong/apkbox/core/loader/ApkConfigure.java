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
