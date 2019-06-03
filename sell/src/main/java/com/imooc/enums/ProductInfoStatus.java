package com.imooc.enums;

import lombok.Getter;

/**
 * @author 王兴明
 * @date 2019/5/13 11:26
 */
@Getter
public enum  ProductInfoStatus implements CodeEnum{
     UP(0, "上架"),
     DOWN(1, "下架");

    private Integer produceStatus;
    private String message;

    ProductInfoStatus(Integer produceStatus, String message){
       this.message = message;
       this.produceStatus = produceStatus;
   }

    @Override
    public Integer getCode() {
        return produceStatus;
    }
}
