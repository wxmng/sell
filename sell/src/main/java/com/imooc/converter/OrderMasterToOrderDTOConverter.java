package com.imooc.converter;

import com.imooc.dataobject.OrderMaster;
import com.imooc.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王兴明
 * @date 2019/5/17 14:37
 */
public class OrderMasterToOrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(OrderMaster orderMaster : orderMasterList){
            orderDTOList.add(convert(orderMaster));
        }
        return orderDTOList;
    }

}
