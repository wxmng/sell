package com.imooc.dao;

import com.imooc.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 王兴明
 * @date 2019/5/12 17:21
 */
public interface ProductInfoDao extends JpaRepository<ProductInfo, String> {
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
