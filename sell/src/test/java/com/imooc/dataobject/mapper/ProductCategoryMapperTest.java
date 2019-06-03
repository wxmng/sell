package com.imooc.dataobject.mapper;

import com.imooc.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author 王兴明
 * @date 2019/6/1 21:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryMapperTest {

    @Autowired
    ProductCategoryMapper mapper;

    @Test
    public void insert(){
        Map<String, Object> map = new HashMap<>();
        map.put("category_name", "更好吃的");
        map.put("category_type", 10);
       int result =  mapper.insert(map);
       Assert.assertEquals(1, result);
    }
    @Test
    public void select(){
        ProductCategory productCategory = new ProductCategory();
        productCategory =  mapper.selectByCategory(8);
       // System.out.println(productCategory);
    }

    @Test
    public void update(){
        mapper.update("我最爱", 8);
    }

    @Test
    public void delete(){
        mapper.delete(10);
    }

}