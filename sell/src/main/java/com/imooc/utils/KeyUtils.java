package com.imooc.utils;

import java.util.Random;

/**
 * @author 王兴明
 * @date 2019/5/15 10:26
 */
public class KeyUtils {

    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}
