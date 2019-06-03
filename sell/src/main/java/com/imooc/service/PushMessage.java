package com.imooc.service;

import com.imooc.dto.OrderDTO;

/**
 * @author 王兴明
 * @date 2019/5/31 21:36
 */
public interface PushMessage {
    void orderStatus(OrderDTO orderDto);
}
