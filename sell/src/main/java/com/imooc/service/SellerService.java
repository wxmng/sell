package com.imooc.service;

import com.imooc.dataobject.SellerInfo;

/**
 * @author 王兴明
 * @date 2019/5/30 21:35
 */
public interface SellerService {
    SellerInfo findSellerInfoByOpenId(String openid);
}
