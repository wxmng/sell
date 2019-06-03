package com.imooc.service.impl;

import com.imooc.VO.ResultVO;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.BuyerService;
import com.imooc.service.OrderService;
import com.imooc.utils.ResultVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 王兴明
 * @date 2019/5/20 16:35
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    OrderService orderService;

    //订单详情
    @Override
    public OrderDTO detail(String openId, String orderId) {

        return checkOrderOwner(openId, orderId);
    }

    //取消订单
    @Override
    public OrderDTO cancel(String openId, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null){
            log.error("[取消订单]查不到订单, orderId = {}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    public OrderDTO checkOrderOwner(String openId, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null) return null;

        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openId)){
            log.error("[订单详情]在数据库中无此openId, openId = {}, orderDTO = {}", openId,orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
