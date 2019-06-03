package com.imooc.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @author 王兴明
 * @date 2019/5/14 11:45
 */

@Getter
public enum  OrderStatusEnum implements CodeEnum{
    NEW(0, "新订单"),
    FINISHED(1, "已完成"),
    CNACEL(2, "已取消"),
    ;


    private Integer status;
    private String msg;

    OrderStatusEnum(Integer status, String msg){
        this.status = status;
        this.msg = msg;
    }


    @Override
    public Integer getCode() {
        return status;
    }
}
