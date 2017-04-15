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
package com.liangmayong.apkbox.core.resources;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by LiangMaYong on 2017/4/6.
 */
public class ApkExtras {

    private ApkExtras() {
    }

    //extras map
    private final static Map<String, Bundle> EXTRAS = new HashMap<String, Bundle>();

    /**
     * putExtras
     *
     * @param extras extras
     */
    public static String putExtras(Bundle extras) {
        String extras_id = System.currentTimeMillis() + "@" + UUID.randomUUID();
        EXTRAS.put(extras_id, extras);
        return extras_id;
    }

    /**
     * getExtras
     *
     * @return extras
     */
    public static Bundle getExtras(String extras_id) {
        if (EXTRAS.containsKey(extras_id)) {
            Bundle extras = EXTRAS.get(extras_id);
            EXTRAS.remove(extras_id);
            return extras;
        }
        return null;
    }

}
