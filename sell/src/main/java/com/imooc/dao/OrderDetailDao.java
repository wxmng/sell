package com.imooc.dao;

import com.imooc.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 王兴明
 * @date 2019/5/14 11:56
 */
public interface OrderDetailDao extends JpaRepository<OrderDetail, String> {
    List<OrderDetail> findByOrderId(String OrdeDetailId);
}
