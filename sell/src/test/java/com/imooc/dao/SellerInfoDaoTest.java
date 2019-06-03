package com.imooc.dao;

import com.imooc.dataobject.SellerInfo;
import com.imooc.utils.KeyUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author 王兴明
 * @date 2019/5/30 20:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoDaoTest {
    @Autowired
    SellerInfoDao sellerInfoDao;

    @Test
    public void save(){

        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtils.genUniqueKey());
        sellerInfo.setUserName("admin");
        sellerInfo.setPassWord("admin");
        sellerInfo.setOpenId("abc");

        SellerInfo result = sellerInfoDao.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void find(){
        SellerInfo sellerInfo = sellerInfoDao.findByOpenId("abc");
        System.out.println(sellerInfo);
    }
}