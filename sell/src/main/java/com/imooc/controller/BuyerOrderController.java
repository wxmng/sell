package com.imooc.controller;

import com.imooc.VO.ResultVO;
import com.imooc.converter.OrderFormToOrderDTOConverter;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.form.OrderForm;
import com.imooc.service.BuyerService;
import com.imooc.service.OrderService;
import com.imooc.utils.ResultVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王兴明
 * @date 2019/5/17 17:25
 */
@RestController
@RequestMapping("buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderFormToOrderDTOConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[创建订单]购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO resultDto = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", resultDto.getOrderId());
        return ResultVOUtils.success(map);
    }


    //查询订单
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openId") String openId,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openId)){
            log.error("[查询订单列表]openId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openId, pageRequest);
        return ResultVOUtils.success(orderDTOPage);
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openId") String openId,
                                     @RequestParam("orderId") String orderId){

        return ResultVOUtils.success(buyerService.detail(openId, orderId));
    }

    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openId") String openId,
                           @RequestParam("orderId") String orderId){

        buyerService.cancel(openId, orderId);
        return ResultVOUtils.success();
    }
}
