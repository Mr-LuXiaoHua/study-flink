package com.example.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luxiaohua
 * @create 2022-06-29 14:42
 */
public class JsonUtils {

    private static Gson gson = new Gson();

    public static  <T> T fromJson(String jsonContent, Class<T> clazz) {
        return gson.fromJson(jsonContent, clazz);
    }

    public static <T> String toJson(Object obj, Class<T> clazz) {
        return gson.toJson(obj, clazz);
    }


    public static <T> List<T> fromJsonList(String jsonContent, Class<T> clazz) {
        return gson.fromJson(jsonContent, new TypeToken<List<T>>(){}.getType());
    }


    public static Map<String, Object> fromJsonToMap(String jsonContent) {
        return gson.fromJson(jsonContent, new TypeToken<HashMap<String, Object>>(){}.getType());
    }

}
