package com.liangmayong.apkbox.core.resources;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by LiangMaYong on 2017/4/6.
 */
public class ApkExtras {

    private ApkExtras() {
    }

    //extras map
    private final static Map<String, Bundle> EXTRAS = new HashMap<String, Bundle>();

    /**
     * putExtras
     *
     * @param extras extras
     */
    public static String putExtras(Bundle extras) {
        String extras_id = System.currentTimeMillis() + "@" + UUID.randomUUID();
        EXTRAS.put(extras_id, extras);
        return extras_id;
    }

    /**
     * getExtras
     *
     * @return extras
     */
    public static Bundle getExtras(String extras_id) {
        if (EXTRAS.containsKey(extras_id)) {
            Bundle extras = EXTRAS.get(extras_id);
            EXTRAS.remove(extras_id);
            return extras;
        }
        return null;
    }

}
