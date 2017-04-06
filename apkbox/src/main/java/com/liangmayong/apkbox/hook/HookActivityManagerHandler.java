package com.liangmayong.apkbox.hook;

import android.annotation.TargetApi;
import android.os.Build;

import com.liangmayong.apkbox.hook.handle.HookProxy_StartActivity;
import com.liangmayong.apkbox.hook.handle.HookProxy_StartService;
import com.liangmayong.apkbox.hook.handle.HookProxy_StopService;

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
        if ("startActivity".equals(method.getName())) {
            return HookProxy_StartActivity.invoke(mBase, method, args);
        } else if ("startService".equals(method.getName()) || "bindService".equals(method.getName())) {
            return HookProxy_StartService.invoke(mBase, method, args);
        } else if ("stopService".equals(method.getName())) {
            return HookProxy_StopService.invoke(mBase, method, args);
        }
        return method.invoke(mBase, args);
    }

}
