package com.liangmayong.apkbox.hook;

import android.annotation.TargetApi;
import android.os.Build;

import com.liangmayong.apkbox.hook.proxy.HookProxy_Activity;
import com.liangmayong.apkbox.hook.proxy.HookProxy_Service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by LiangMaYong on 2016/9/19.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class HookActivityManagerHandler implements InvocationHandler {

    private Object mBase;

    public HookActivityManagerHandler(Object base) {
        this.mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().contains("startActivity")) {
            return HookProxy_Activity.invoke(mBase, method, args);
        } else if ("startService".equals(method.getName())
                || "stopService".equals(method.getName())
                || "bindService".equals(method.getName())
                || "unbindService".equals(method.getName())
                ) {
            return HookProxy_Service.invoke(mBase, method, args);
        }
        return method.invoke(mBase, args);
    }

}
