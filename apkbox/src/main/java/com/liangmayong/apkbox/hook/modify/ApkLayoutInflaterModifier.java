package com.liangmayong.apkbox.hook.modify;

import android.view.LayoutInflater;
import android.view.View;

import com.liangmayong.apkbox.reflect.ApkReflect;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * Created by LiangMaYong on 2016/9/21.
 */
public class ApkLayoutInflaterModifier {

    private ApkLayoutInflaterModifier() {
    }

    static HashMap<String, Constructor<? extends View>> sConstructorMap = null;

    static {
        try {
            sConstructorMap = (HashMap<String, Constructor<? extends View>>) ApkReflect.getField(LayoutInflater.class, null, "sConstructorMap");
        } catch (Throwable e) {
        }
    }

    public static void modify() {
        if (sConstructorMap != null) {
            sConstructorMap.clear();
        }
    }
}