package com.imooc.service.impl;

import com.imooc.dataobject.ProductInfo;
import com.imooc.enums.ProductInfoStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 王兴明
 * @date 2019/5/13 11:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProduceServiceImplTest {

    @Autowired
    ProduceServiceImpl produceService;

    @Test
    public void findOne() {
        ProductInfo result = produceService.findOne("123457");
        System.out.println(result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> list = produceService.findUpAll();
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void findAll() {
        //定义页面请求信息详情，其中包含三个构造方法，本构造方法为选择页数和条数
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> productInfoPage = produceService.findAll(request);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123459");
        productInfo.setProductName("冰激凌");
        productInfo.setProductPrice(new BigDecimal(15.2));
        productInfo.setProductStock(1000);
        productInfo.setProductDescription("女孩子爱吃");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(ProductInfoStatus.UP.getProduceStatus());
        productInfo.setCategoryType(4);

        ProductInfo result = produceService.save(productInfo);
        Assert.assertNotNull(result);
    }
}