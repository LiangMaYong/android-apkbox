package com.liangmayong.apkbox.hook.proxy;

import android.content.Intent;
import android.util.Pair;

import com.liangmayong.apkbox.hook.activity.HookActivity_Component;
import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Method;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookProxy_StartActivity {

    private HookProxy_StartActivity() {
    }

    public static Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
