package com.imooc.dao;

import com.imooc.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 王兴明
 * @date 2019/5/30 20:11
 */
public interface SellerInfoDao extends JpaRepository<SellerInfo, String> {
    SellerInfo findByOpenId(String openId);
}
