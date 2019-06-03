package com.imooc.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author 王兴明
 * @date 2019/5/25 12:55
 */
public class JsonUtils {

    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
