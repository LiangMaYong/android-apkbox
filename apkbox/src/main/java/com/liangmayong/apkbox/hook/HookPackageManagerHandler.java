package com.liangmayong.apkbox.hook;

import android.annotation.TargetApi;
import android.os.Build;

import com.liangmayong.apkbox.hook.method.HookGetActivityInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by LiangMaYong on 2016/9/19.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class HookPackageManagerHandler implements InvocationHandler {

    private Object mBase;

    public HookPackageManagerHandler(Object base) {
        this.mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("getActivityInfo".equals(method.getName())) {
            return HookGetActivityInfo.getActivityInfo(mBase, method, args);
        }
        return method.invoke(mBase, args);
    }
}
