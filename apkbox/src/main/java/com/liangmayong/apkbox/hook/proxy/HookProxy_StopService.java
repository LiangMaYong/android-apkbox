package com.liangmayong.apkbox.hook.proxy;

import android.content.Intent;
import android.util.Pair;

import com.liangmayong.apkbox.hook.component.HookComponent_Service;
import com.liangmayong.apkbox.hook.service.HookService_ServiceManager;
import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Method;

/**
 * Created by LiangMaYong on 2017/4/5.
 */
public class HookProxy_StopService {

    private HookProxy_StopService() {
    }

    public static Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ApkLogger.get().debug("hook proxy " + method.getName(), null);
        Pair<Integer, Intent> pairPairPair = getArgsPair(args);
        if (pairPairPair.first != -1) {
            HookService_ServiceManager.addStopService(pairPairPair.second);
            int intentIndex = pairPairPair.first;
            Intent newIntent = HookComponent_Service.modify(pairPairPair.second);
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
