package com.imooc.dao;

import com.imooc.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 王兴明
 * @date 2019/5/14 11:56
 */
public interface OrderMasterDao extends JpaRepository<OrderMaster, String> {
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenId, Pageable pageable);
}
