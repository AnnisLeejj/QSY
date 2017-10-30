package com.heking.qsy.util;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class ParsonJson {

    private static Gson mGson = new Gson();

	/*
	 * json为数组的时候传type集合数组
	 * Type listType = new TypeToken<List<Object>>() {}.getType();  
	 * json为对象的时候传type对象
	 *  Type listType = new TypeToken<Object>() {}.getType();
	 */

    public static <T> T jsonToBean(String json, Type beanType) {
        try {
            return mGson.fromJson(json, beanType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /*
     * json为对象的时候传class和上面的type是一样的
     */
    public static <T> T jsonToBeanObj(String json, Class<T> c) {
        try {
            Log.i("ParsonJson", "json:" + json);
            return mGson.fromJson(json, c);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String beanToJson(Object bean) {
        return mGson.toJson(bean);
    }

    /**
     * 将Map转化为Json
     *
     * @param map
     * @return String
     */
    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }

    public static Map<String, String> toMap(String str) {
        Gson gson = new Gson();
        Map<String, String> gsonMap = gson.fromJson(str, new TypeToken<Map<String, String>>() {
        }.getType());
        return gsonMap;
    }
}
