package com.liangmayong.apkbox.reflect;

import com.liangmayong.apkbox.utils.ApkLogger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangmayong on 2016/9/18.
 */
public final class ApkReflect {

    private ApkReflect() {
    }

    /**
     * isGeneric
     *
     * @param clazz clazz
     * @param name  name
     * @return true or false
     */
    public static boolean isGeneric(Class<?> clazz, String name) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            if (name.equals(clazz.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * setField
     *
     * @param clazz     clazz
     * @param object    object
     * @param fieldName fieldName
     * @param value     value
     * @return true or false
     */
    public static boolean setField(Class<?> clazz, Object object, String fieldName, Object value) {
        Field field = null;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (Exception e) {
            }
        }
        if (field != null) {
            field.setAccessible(true);
            try {
                field.set(object, value);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * setField
     *
     * @param clazz     clazz
     * @param object    object
     * @param fieldName fieldName
     * @return object
     */
    public static final Object getField(Class<?> clazz, Object object, String fieldName) {
        if (clazz == null) {
            return null;
        }
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(object);
            } catch (Exception e) {
            }
        }
        return null;
    }


    /**
     * getFields
     *
     * @param clazz  clazz
     * @param object object
     * @return fields
     */
    public static Map<String, Object> getFields(Class<?> clazz, Object object) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (clazz != null) {
            for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
                Field[] fields = clazz.getDeclaredFields();
                if (fields != null && fields.length > 0) {
                    for (int i = 0; i < fields.length; i++) {
                        Object value = null;
                        try {
                            fields[i].setAccessible(true);
                            value = fields[i].get(object);
                        } catch (Exception e1) {
                        }
                        map.put(fields[i].getName(), value);
                    }
                }
            }
        }
        return map;
    }

    /**
     * print
     *
     * @param object object
     */
    public static void print(Object object) {
        if (object != null) {
            Map<String, Object> fields = getFields(object.getClass(), object);
            ApkLogger.get().debug("==================================================", null);
            ApkLogger.get().debug("---------> print " + object.getClass().getName() + "(" + object + ")", null);
            for (Map.Entry<String, Object> entry : fields.entrySet()) {
                ApkLogger.get().debug("---------> " + entry.getKey() + " = " + entry.getValue(), null);
            }
        } else {
            ApkLogger.get().debug("==================================================", null);
            ApkLogger.get().debug("---------> print null", null);
        }
        ApkLogger.get().debug("==================================================", null);
    }

    /**
     * cloneModel
     *
     * @param object       object
     * @param targetObject targetObject
     */
    public static void cloneModel(Object object, Object targetObject) {
        if (object != null) {
            Map<String, Object> fields = getFields(object.getClass(), object);
            if (fields != null && !fields.isEmpty()) {
                for (Map.Entry<String, Object> entry : fields.entrySet()) {
                    setField(targetObject.getClass(), targetObject, entry.getKey(), entry.getValue());
                }
            }
        }
    }

}
