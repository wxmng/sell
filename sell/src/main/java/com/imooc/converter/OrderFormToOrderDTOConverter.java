package com.imooc.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.dataobject.OrderDetail;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王兴明
 * @date 2019/5/17 17:39
 */
@Slf4j
public class OrderFormToOrderDTOConverter {

    //把表单装换成orderDTO
    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();
        
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenId());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
           orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e){
            log.error("[对象转换错误], String = {}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
            orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
