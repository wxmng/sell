package com.imooc.enums;

import lombok.Getter;

/**
 * @author 王兴明
 * @date 2019/5/14 11:51
 */
@Getter
public enum PayStatusEnum implements CodeEnum{
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
        ;


    private Integer status;
    private String msg;

    PayStatusEnum(Integer status, String msg){
        this.status = status;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return status;
    }

}
