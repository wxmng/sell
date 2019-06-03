package com.imooc.utils;

import com.imooc.enums.CodeEnum;

/**
 * @author 王兴明
 * @date 2019/5/28 12:05
 */
public class EnumUtils {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for(T each : enumClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
