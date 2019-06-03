package com.imooc.enums;

import lombok.Getter;

/**
 * @author 王兴明
 * @date 2019/5/15 10:06
 */
@Getter
public enum ResultEnum {


    PARAM_ERROR(1, "参数错误"),
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存不正确"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDERDETAIL_NOT_EXIST(13, "订单集合不存在"),
    OEDER_STATUS_REEOR(14, "订单状态不正确"),
    ORDER_UPDATE_FAIL(15, "订单更新失败"),
    ORDER_DETAILS_EMPTY(16, "订单中无详情"),
    ORDER_PAY_STATUS_ERROR(17, "订单支付状态不正确"),
    CART_EMPTY(18, "购物车为空"),
    ORDER_OWNER_ERROR(19, "该订单不属于当前用户"),
    WECHAT_MP_ERROR(20, "微信授权错误"),
    WXPAY_NOTIFY_MONEY_VERIFY(21, "微信支付异步通知金额不一致"),
    ORDER_CANCEL_SUCCESS(22, "订单取消成功"),
    ORDER_FINISH_SUCCESS(23, "订单完结成功"),
    PRODUCT_STATUS_ERROR(24, "订单状态错误"),
    LOGIN_FAIL(25, "登录错误"),
    LOGOUT_SUCCESS(26, "登出成功"),
    ;


    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
