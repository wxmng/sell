package com.imooc.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 王兴明
 * @date 2019/5/17 17:20
 */
@Data
public class OrderForm {

    @NotNull(message="买家姓名必填")
    private String name;

    @NotNull(message="买家电话必填")
    private String phone;

    @NotNull(message="买家地址必填")
    private String address;

    @NotNull(message="买家ipenId必填")
    private String openId;

    @NotNull(message="买家购物车必填")
    private String items;
}
