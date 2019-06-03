package com.imooc.service.impl;

import com.imooc.config.WechatAccountConfig;
import com.imooc.config.WechatPayConfig;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.PayService;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MathUtils;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author 王兴明
 * @date 2019/5/24 15:13
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    BestPayServiceImpl bestPayService;

    @Autowired
    OrderServiceImpl orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getOrderId());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("[微信支付], payRequest={}", JsonUtils.toJson(payRequest));


        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付, payResponse={}", JsonUtils.toJson(payResponse));

        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("异步通知, payRespond={}", JsonUtils.toJson(payResponse));

        //查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        if(orderDTO == null){
            log.error("[微信支付] 异步通知订单不存在, orderId={}", orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //判断订单金额是否一致
        if(!MathUtils.equals(orderDTO.getOrderAmount().doubleValue(), payResponse.getOrderAmount())){
            log.error("[微信支付] 订单金额不一直, orderId={}, 通知金额={}, 系统金额={}",
                    payResponse.getOrderId(), payResponse.getOrderAmount(), orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY);
        }

        //修改订单状态
        orderService.paid(orderDTO);
        return payResponse;
    }

    @Override
    public RefundResponse refound(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信退款], refundRequest={}", refundRequest);

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("[微信退款]，refundResponse={}", refundResponse);
        return refundResponse;
    }
}
