package com.yz.appdemo.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * SharePreference工具类
 */
public class SPUtil {

    public static final String DATA = "data";
    public static final String CONFIG = "config";

    //账号
    public static final String ACCOUNT = "account";
    public static final String PWD = "pwd";
    //服务器ip
    public static final String SERVER_IP = "server_ip";


    /**
     * @param fileName 文件名称
     * @param key      key
     * @param object   保存的对象  不支持double
     */
    public static void setParam(Context context, String fileName, String key, Object object) {
        if (object == null) {
            return;
        }
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }
        editor.apply();
    }


    /**
     * @param fileName      文件名称
     * @param key           key
     * @param defaultObject 默认返回值
     * @return 获取的对象
     */
    public static Object getParam(Context context, String fileName, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        if (sp == null) {
            return defaultObject;
        }
        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return defaultObject;
    }

    /**
     * clean data
     *
     * @param fileName
     */
    public static void cleanData(Context context, String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
