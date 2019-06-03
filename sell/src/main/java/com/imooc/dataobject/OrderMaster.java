package com.imooc.dataobject;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 王兴明
 * @date 2019/5/14 11:42
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    @Id
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
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;
}
