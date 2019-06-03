package com.imooc.dao;

import com.imooc.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 王兴明
 * @date 2019/5/12 13:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryDaoTest {

    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void findOneTest(){
        ProductCategory productCategory = categoryDao.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory("女生最爱",4);
       ProductCategory result = categoryDao.save(productCategory);
        Assert.assertNotNull(result);//查看结果是否成功
        //或者用第一个为不希望的第二个为实际的
        //Assert.assertNotEquals(0, result);
    }

    @Test
    public void findByCategorytypeInTest(){
        List<ProductCategory> list = categoryDao.findByCategoryTypeIn(Arrays.asList(1,2,3));
        Assert.assertNotEquals(0, list);
        System.out.println(list);
    }
}