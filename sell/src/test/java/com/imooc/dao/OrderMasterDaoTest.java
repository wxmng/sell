package com.imooc.dao;

import com.imooc.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author 王兴明
 * @date 2019/5/14 12:14
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {

    @Autowired
    OrderMasterDao orderMasterDao;

    private final String OPENID = "110110";

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234568");
        orderMaster.setBuyerName("师弟");
        orderMaster.setBuyerPhone("12874567123");
        orderMaster.setBuyerAddress("幕课网");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(3.2));

        OrderMaster result = orderMasterDao.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenidTest(){
        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderMaster> result = orderMasterDao.findByBuyerOpenid(OPENID, pageRequest);
        Assert.assertNotEquals(0, result.getTotalElements());
    }
}