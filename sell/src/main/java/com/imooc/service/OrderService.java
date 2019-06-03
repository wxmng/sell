package com.imooc.service;

import com.imooc.dataobject.OrderDetail;
import com.imooc.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author 王兴明
 * @date 2019/5/14 15:07
 */
public interface OrderService {

    //创建订单
    public OrderDTO create(OrderDTO orderDTO);

    //查找订单
    public OrderDTO findOne(String orderId);

    //查找订单列表
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable);

    //取消订单
    public OrderDTO cancel(OrderDTO orderDTO);

    //完成订单
    public OrderDTO finish(OrderDTO orderDTO);

    //支付订单
    public OrderDTO paid(OrderDTO orderDTO);

    //查询订单列表
    //查找订单列表
    public Page<OrderDTO> findList(Pageable pageable);

}
