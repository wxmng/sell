package com.imooc.service.impl;

import com.imooc.dataobject.OrderDetail;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 王兴明
 * @date 2019/5/17 9:58
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    private final String BUYER_OPENID = "1101111";

    private final String ORDER_ID = "1558080393888848383";

    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setBuyerName("老四");
            orderDTO.setBuyerAddress("大靖");
            orderDTO.setBuyerPhone("18868822111");
            orderDTO.setBuyerOpenid("acd15sd8erwtgv2");

            List<OrderDetail> orderDetailList = new ArrayList<>();
            OrderDetail orderDetail1 = new OrderDetail();
            orderDetail1.setProductId("123457");
            orderDetail1.setProductQuantity(5);

            OrderDetail orderDetail2 = new OrderDetail();
            orderDetail2.setProductId("123459");
            orderDetail2.setProductQuantity(10);
            orderDetailList.add(orderDetail1);
            orderDetailList.add(orderDetail2);
            orderDTO.setOrderDetailList(orderDetailList);
            OrderDTO result = orderService.create(orderDTO);
            log.info("[创建订单] result = {}", result);
            Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO result = orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】result={}", result);
        Assert.assertEquals(ORDER_ID, result.getOrderId());
    }

    @Test
    public void findList() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, pageRequest);
        log.info("【订单列表查询】 result={}", orderDTOPage);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO  = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CNACEL.getStatus(), result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO  = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getStatus(), result.getOrderStatus());
    }

    @Test
    public void paid(){
        OrderDTO orderDTO  = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getStatus(), result.getPayStatus());
    }

    @Test
    public void findList1() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderDTO> orderDTOList = orderService.findList(pageRequest);
        Assert.assertNotEquals(0, orderDTOList.getTotalElements());
    }
}