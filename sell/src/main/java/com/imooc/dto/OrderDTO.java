package com.imooc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.dataobject.OrderDetail;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.utils.EnumUtils;
import com.imooc.utils.serializer.DateToLongSerializer;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 王兴明
 * @date 2019/5/14 15:08
 */
@Data
public class OrderDTO {

    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /**订单状态*/
    private Integer orderStatus = OrderStatusEnum.NEW.getStatus();

    /**支付状态*/
    private Integer payStatus = PayStatusEnum.WAIT.getStatus();

    /** 创建时间. */
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date updateTime;

    /**订单集合*/
    List<OrderDetail> orderDetailList;

    /**订单状态表述*/
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtils.getByCode(orderStatus, OrderStatusEnum.class);
    }

    /**支付状态表述*/
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtils.getByCode(payStatus, PayStatusEnum.class);
    }
}
