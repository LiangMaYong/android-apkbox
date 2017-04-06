package com.liangmayong.apkbox.hook.handle;

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
        ApkLogger.get().debug("hook startActivity", null);
        Pair<Pair<Integer, Intent>, Pair<Integer, String>> pairPairPair = pairArgs(args);
        if (pairPairPair.first.first != -1 && pairPairPair.second.first != -1) {
            int intentIndex = pairPairPair.first.first;
            Intent raw = pairPairPair.first.second;
            String targetPackage = pairPairPair.second.second;
            args[intentIndex] = HookActivity_Component.modify(raw, targetPackage);
        }
        return method.invoke(proxy, args);
    }

    /**
     * foundFirstIntentOfArgs
     *
     * @param args args
     * @return pairs
     */
    private static Pair<Pair<Integer, Intent>, Pair<Integer, String>> pairArgs(Object... args) {
        int intentIndex = -1;
        Intent intent = null;
        int packageIndex = -1;
        String packageName = "";
        for (int i = 0; i < args.length; i++) {
            if (packageIndex == -1 && args[i] instanceof String) {
                packageIndex = i;
                packageName = (String) args[i];
                break;
            }
            if (intentIndex == -1 && args[i] instanceof Intent) {
                intentIndex = i;
                intent = (Intent) args[i];
                break;
            }
        }
        return Pair.create(Pair.create(intentIndex, intent), Pair.create(packageIndex, packageName));
    }
}
