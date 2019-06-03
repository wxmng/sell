package com.imooc.controller;

import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.OrderService;
import com.imooc.service.impl.PayServiceImpl;
import com.lly835.bestpay.model.PayResponse;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author 王兴明
 * @date 2019/5/24 15:08
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    OrderService orderService;

    @Autowired
    PayServiceImpl payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                      @RequestParam("returnUrl") String returnUrl,
                              Map<String, Object> map){
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        PayResponse payResponse = payService.create(orderDTO);
            map.put("payResponse", payResponse);
            map.put("returnUrl", returnUrl);
        return new ModelAndView("pay/create", map);
    }


    /*
     微信异步通知
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){
        payService.notify(notifyData);

        return new ModelAndView("pay/success");
    }


}
