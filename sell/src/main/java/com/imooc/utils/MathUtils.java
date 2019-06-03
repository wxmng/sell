package com.imooc.utils;

/**
 * @author 王兴明
 * @date 2019/5/25 17:07
 */
public class MathUtils {
    private static final double MONEY_RAGE = 0.01;

    public static boolean equals(double d1, double d2){
        if(Math.abs(d1-d2) < MONEY_RAGE){
            return true;
        } else {
            return false;
        }
    }
}
