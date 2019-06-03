package com.imooc.service.impl;

import com.imooc.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 王兴明
 * @date 2019/5/12 16:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory result = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1), result.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> list = categoryService.findAll();
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> list = categoryService.findByCategoryTypeIn(Arrays.asList(2,3,10));
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void save() {
        ProductCategory pc = new ProductCategory("儿童专享", 5);
        ProductCategory result = categoryService.save(pc);
        Assert.assertNotNull(result);
    }
}