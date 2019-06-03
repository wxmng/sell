package com.imooc.service.impl;

import com.imooc.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author 王兴明
 * @date 2019/5/31 21:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PushMessageImplTest {

    @Autowired
    private PushMessageImpl pushMessage;

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void orderStatus() {
        OrderDTO orderDTO = orderService.findOne("1558080393888848382");
        pushMessage.orderStatus(orderDTO);
    }
}