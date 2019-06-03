package com.imooc.service.impl;

import com.imooc.dto.OrderDTO;
import com.imooc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author 王兴明
 * @date 2019/5/24 16:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    PayServiceImpl payService;

    @Test
    public void create() {
        OrderDTO orderDTO = orderService.findOne("1558080393888848382");
        payService.create(orderDTO);
    }

    @Test
    public void refund() {
        OrderDTO orderDTO = orderService.findOne("1558080393888848382");
        payService.refound(orderDTO);
    }
}