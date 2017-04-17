/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017 LiangMaYong ( ibeam@qq.com )
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/ or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 **/
package com.liangmayong.apkbox.core.loader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import com.liangmayong.apkbox.core.ApkLoaded;
import com.liangmayong.apkbox.reflect.ApkReflect;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by LiangMaYong on 2016/9/20.
 */
public final class ApkReceiver {

    // BROADCAST_RECEIVER_MAP
    private static final Map<String, LinkedList<BroadcastReceiver>> BROADCAST_RECEIVER_MAP = new HashMap<String, LinkedList<BroadcastReceiver>>();
    // CONTEXT_MAP
    private static final Map<String, Context> CONTEXT_MAP = new HashMap<String, Context>();

    /**
     * unregisterReceiver
     *
     * @param loaded loaded
     */
    public static void unregisterReceiver(ApkLoaded loaded) {
        String key = loaded.getApkPath();
        if (!CONTEXT_MAP.containsKey(key)) {
            return;
        }
        Context context = CONTEXT_MAP.get(key);
        if (BROADCAST_RECEIVER_MAP.containsKey(key)) {
            LinkedList<BroadcastReceiver> receivers = BROADCAST_RECEIVER_MAP.get(key);
            for (int i = 0; i < receivers.size(); i++) {
                context.unregisterReceiver(receivers.get(i));
            }
            BROADCAST_RECEIVER_MAP.remove(key);
        }
        CONTEXT_MAP.containsKey(key);
    }

    /**
     * registerReceiver
     *
     * @param context context
     * @param loaded  loaded
     */
    public static void registerReceiver(Context context, ApkLoaded loaded) {
        if (!ApkProcess.validateApkProcessName(context)) {
            return;
        }
        String key = loaded.getApkPath();
        if (BROADCAST_RECEIVER_MAP.containsKey(key)) {
            return;
        }
        if (loaded != null) {
            Context ctx = context.getApplicationContext();
            CONTEXT_MAP.put(key, ctx);
            Map<String, IntentFilter> filters = loaded.getFilters();
            LinkedList<BroadcastReceiver> receivers = new LinkedList<BroadcastReceiver>();
            for (Map.Entry<String, IntentFilter> entry : filters.entrySet()) {
                String clazzName = ApkLoader.parserClassName(loaded.getApkInfo().packageName, entry.getKey());
                try {
                    Class<?> clazz = loaded.getClassLoader().loadClass(clazzName);
                    if (clazz != null && ApkReflect.isGeneric(clazz, BroadcastReceiver.class.getName())) {
                        try {
                            IntentFilter intentFilter = new IntentFilter();
                            IntentFilter raw = entry.getValue();
                            int count = raw.countActions();
                            for (int i = 0; i < count; i++) {
                                String action = raw.getAction(i);
                                if (action.contains(loaded.getApkInfo().packageName)) {
                                    action = action.replaceAll(loaded.getApkInfo().packageName, context.getPackageName());
                                }
                                intentFilter.addAction(action);
                            }
                            BroadcastReceiver broadcastReceiver = (BroadcastReceiver) clazz.newInstance();
                            ctx.registerReceiver(broadcastReceiver, intentFilter);
                            receivers.add(broadcastReceiver);
                        } catch (Exception e) {
                        }
                    }
                } catch (ClassNotFoundException e) {
                }
            }
            BROADCAST_RECEIVER_MAP.put(key, receivers);
        }
    }
}
