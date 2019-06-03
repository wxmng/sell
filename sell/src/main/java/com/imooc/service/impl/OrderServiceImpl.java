package com.imooc.service.impl;

import com.imooc.converter.OrderMasterToOrderDTOConverter;
import com.imooc.dao.OrderDetailDao;
import com.imooc.dao.OrderMasterDao;
import com.imooc.dataobject.OrderDetail;
import com.imooc.dataobject.OrderMaster;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.OrderService;
import com.imooc.service.ProductService;
import com.imooc.service.WebSocket;
import com.imooc.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王兴明
 * @date 2019/5/14 15:40
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    @Autowired
    ProductService productService;

    @Autowired
    OrderDetailDao orderDetailDao;

    @Autowired
    OrderMasterDao orderMasterDao;

    @Autowired
    PayServiceImpl payService;

    @Autowired
    PushMessageImpl pushMessage;

    @Autowired
    WebSocket webSocket;


    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtils.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);
        //List<CartDTO> cartDTOList = new ArrayList<>();

        //查询商品
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //计算订单总价
            orderAmount = productInfo.getProductPrice()
                .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //详情订单入库
            orderDetail.setDetailId(KeyUtils.genUniqueKey());
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetailDao.save(orderDetail);

            //添加购物车商品
//            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
//            cartDTOList.add(cartDTO);
        }
        //写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getStatus());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getStatus());
        orderMasterDao.save(orderMaster);

        //扣库存
        //4. 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        //webSocket.sendMessage(orderDTO.getOrderId());
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterDao.findOne(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        if(orderDetailList == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(buyerOpenId, pageable);
        List<OrderDTO> orderDTOList = OrderMasterToOrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //查询订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getStatus())){
            log.error("[订单取消]订单状态不正确， orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.OEDER_STATUS_REEOR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CNACEL.getStatus());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterDao.save(orderMaster);
        if(result == null){
            log.error("[取消订单]更新失败， orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单]订单中无商品详情， orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAILS_EMPTY);
        }
        List<CartDTO> cartDTOList = new ArrayList<>();
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            cartDTOList.add(new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity()));
        }
        productService.increaseStock(cartDTOList);
        //如果已付款需要退款
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getStatus())){
            //TODO
            //payService.refound(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //查询订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getStatus())){
            log.error("[订单取消]订单状态不正确， orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getStatus());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterDao.save(orderMaster);
        if(result == null){
            log.error("[完结订单]完结失败， orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        pushMessage.orderStatus(orderDTO);
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //查询订单
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getStatus())){
            log.error("[订单支付]订单状态不正确， orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.OEDER_STATUS_REEOR);
        }
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getStatus())){
            log.error("[订单支付]支付状态不正确， orderDTO = {}", orderDTO);
            throw new SellException(ResultEnum.OEDER_STATUS_REEOR);
        }
        //修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getStatus());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterDao.save(orderMaster);
        if(result == null){
            log.error("【订单支付完成】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMasterToOrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }
}
