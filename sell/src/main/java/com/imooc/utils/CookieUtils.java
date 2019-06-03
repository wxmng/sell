package com.imooc.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 王兴明
 * @date 2019/5/31 15:56
 */
public class CookieUtils {

    public static void set(HttpServletResponse response, String name,
                           String value, Integer maxAge){
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);

    }

    public static Cookie get(HttpServletRequest request,
                             String name){
        Map<String, Cookie> mapCookie = readCookieMap(request);
        if(mapCookie.containsKey(name)){
            return mapCookie.get(name);
        } else {
            return null;
        }
    }

    private static Map<String, Cookie> readCookieMap(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Map<String, Cookie> map = new HashMap<>();
        if(cookies != null){
            for(Cookie cookie : cookies){
                map.put(cookie.getName(), cookie);
            }
        }
        return map;
    }
}
