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
package com.liangmayong.apkbox.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * Created by LiangMaYong on 2017/4/12.
 */
public class ApkMd5 {

    private ApkMd5() {
    }

    public final static String md5(String str) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = str.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte tmp[] = mdTemp.digest();
            char strs[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                strs[k++] = hexDigits[byte0 >>> 4 & 0xf];
                strs[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(strs).toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }

    public static String sha1(File file) {
        String value = "";
        try {
            FileInputStream in = new FileInputStream(file);
            try {
                MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
                MessageDigest md5 = MessageDigest.getInstance("SHA1");
                md5.update(byteBuffer);
                BigInteger bi = new BigInteger(1, md5.digest());
                value = bi.toString(16);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != in) {
                    try {
                        in.close();
                    } catch (IOException e) {
                    }
                }
            }
        } catch (Exception e) {
        }
        return value;
    }

}
