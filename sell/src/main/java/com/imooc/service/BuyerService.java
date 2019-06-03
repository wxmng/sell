package com.imooc.service;

import com.imooc.VO.ResultVO;
import com.imooc.dto.OrderDTO;

/**
 * @author 王兴明
 * @date 2019/5/20 16:33
 */
public interface BuyerService {
    OrderDTO detail(String openId, String orderId);

    OrderDTO cancel(String openId, String orderId);
}
