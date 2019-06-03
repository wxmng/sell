package com.imooc.service.impl;

import com.imooc.dao.SellerInfoDao;
import com.imooc.dataobject.SellerInfo;
import com.imooc.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 王兴明
 * @date 2019/5/30 21:36
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    SellerInfoDao sellerInfoDao;

    @Override
    public SellerInfo findSellerInfoByOpenId(String openid) {
        return sellerInfoDao.findByOpenId(openid);
    }
}
