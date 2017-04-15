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
package com.liangmayong.apkbox.hook.proxy;

import android.content.Intent;
import android.util.Pair;

import com.liangmayong.apkbox.hook.activity.HookActivity_Component;
import com.liangmayong.apkbox.utils.ApkBuild;
import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Method;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookProxy_Activity {

    private static final boolean DEBUG_HOOK_PROXY = ApkBuild.DEBUG_HOOK_PROXY;

    private HookProxy_Activity() {
    }

    public static Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (DEBUG_HOOK_PROXY)
            ApkLogger.get().debug("hook proxy " + method.getName(), null);
        Pair<Integer, Intent> pairPairPair = getArgsPair(args);
        if (pairPairPair.first != -1) {
            int intentIndex = pairPairPair.first;
            Intent newIntent = HookActivity_Component.modify(pairPairPair.second);
            args[intentIndex] = newIntent;
        }
        return method.invoke(proxy, args);
    }

    /**
     * getArgsPair
     *
     * @param args args
     * @return pairs
     */
    private static Pair<Integer, Intent> getArgsPair(Object... args) {
        int intentIndex = -1;
        Intent intent = null;
        for (int i = 0; i < args.length; i++) {
            if (intentIndex == -1 && args[i] instanceof Intent) {
                intentIndex = i;
                intent = (Intent) args[i];
                break;
            }
        }
        return Pair.create(intentIndex, intent);
    }
}
