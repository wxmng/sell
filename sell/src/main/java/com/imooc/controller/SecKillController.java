package com.imooc.controller;

import com.imooc.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author 王兴明
 * @date 2019/6/3 16:40
 */
@RestController
@RequestMapping("/skill")
public class SecKillController {

    @Autowired
    private SecKillService secKillService;

    /*
        查询商品剩余信息
        param productId
        return
     */
    @GetMapping("/query/{productId}")
    public String query(@PathVariable String productId) throws Exception{
        return secKillService.querySecKillProductInfo(productId);
    }


    @GetMapping("/skill/{productId}")
    public String kill(@PathVariable String productId) throws Exception{
        secKillService.orderProductMockDiffUser(productId);
        return secKillService.querySecKillProductInfo(productId);
    }

}
