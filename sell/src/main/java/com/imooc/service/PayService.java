package com.imooc.service;

import com.imooc.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;

/**
 * @author 王兴明
 * @date 2019/5/24 15:12
 */
public interface PayService {
    public PayResponse create(OrderDTO orderDTO);

    public PayResponse notify(String notifyData);

    public RefundResponse refound(OrderDTO orderDTO);
}
