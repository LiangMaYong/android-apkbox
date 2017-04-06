package com.liangmayong.apkbox.hook.handle;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Pair;

import com.liangmayong.apkbox.core.constant.ApkConstant;
import com.liangmayong.apkbox.proxy.ProxyService;
import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Method;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookProxy_StartService {

    private HookProxy_StartService() {
    }

    public static Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ApkLogger.get().debug("hook startService", null);
        Pair<Integer, Intent> integerIntentPair = foundFirstIntentOfArgs(args);
        Intent raw = integerIntentPair.second;
        Pair<Integer, String> integerStringPair = foundFirstStringOfArgs(args);
        String targetPackage = integerStringPair.second;
        if (raw.hasExtra(ApkConstant.EXTRA_APK_PATH)) {
            Intent newIntent = new Intent();
            ComponentName componentName = new ComponentName(targetPackage, ProxyService.class.getCanonicalName());
            newIntent.setComponent(componentName);
            newIntent.putExtra(ApkConstant.EXTRA_TARGET_INTENT, raw);
            args[integerIntentPair.first] = newIntent;
        }
        return method.invoke(proxy, args);
    }

    /**
     * foundFirstStringOfArgs
     *
     * @param args args
     * @return pairs
     */
    private static Pair<Integer, String> foundFirstStringOfArgs(Object... args) {
        int index = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String) {
                index = i;
                break;
            }
        }
        return Pair.create(index, (String) args[index]);
    }

    /**
     * foundFirstIntentOfArgs
     *
     * @param args args
     * @return pairs
     */
    private static Pair<Integer, Intent> foundFirstIntentOfArgs(Object... args) {
        int index = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Intent) {
                index = i;
                break;
            }
        }
        return Pair.create(index, (Intent) args[index]);
    }

}
