package com.example.a515_01.homework1.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * 配置工具类
 */
public class ConfigUtils {
    public static final String FILE_NAME = "config_sp";

    /**
     * 是否第一次启动
     *
     * @return
     */
    public static boolean isFirstStart(Context context) {
        //判断是不是第一次启动
        if ((Boolean) ConfigUtils.get(context, "isFirstStart", true)) {
            ConfigUtils.put(context, "isFirstStart", false);//添加,设置不是第一次启动
            return true;//是第一次启动
        } else {
            return false;//不是第一次启动
        }
    }

    /**
     * 获取用户名
     * @param context
     * @return
     */
    public static String getUsername(Context context) {
        return (String) get(context, "username", "");
    }

    /**
     * 获取指定数据
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object get(Context context, String key, Object defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defaultValue instanceof Boolean) {//判断对象类型
            return sp.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sp.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sp.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sp.getLong(key, (Long) defaultValue);
        } else if (defaultValue instanceof String) {
            return sp.getString(key, (String) defaultValue);
        }
        return null;
    }

    /**
     * 保存指定数据
     */
    public static void put(Context context, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, (String) value);
        }
        editor.commit();
    }

    /**
     * 删除指定数据
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);//删除
        editor.commit();//提交
    }

    /**
     * 检查对应数据是否存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 获取所有的数据
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 删除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

}
