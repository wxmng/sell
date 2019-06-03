package com.imooc.service.impl;

import com.imooc.exception.SellException;
import com.imooc.service.RedisLock;
import com.imooc.service.SecKillService;
import com.imooc.utils.KeyUtils;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王兴明
 * @date 2019/6/3 16:59
 */
@Service
public class SecKillServiceImpl implements SecKillService {
    private static final int OUTTIME= 1000 * 10;

    @Autowired
    private RedisLock redisLock;

    static Map<String, Integer> products;
    static Map<String, Integer> stock;
    static Map<String, String> order;
    static {
        products = new HashMap<>();
        stock = new HashMap<>();
        order = new HashMap<>();
        stock.put("123456", 1000000);
        products.put("123456", 1000000);
    }

    private String queryMap(String productId){
        return "618活动有  " + products.get(productId) +
                "    库存还剩          " + stock.get(productId) +
                "成功下单人数          " + order.size() + "  人";
    }



    @Override
    public String querySecKillProductInfo(String productId) {
        return this.queryMap(productId);
    }

    @Override
    public void orderProductMockDiffUser(String productId) {
        long time = System.currentTimeMillis() + OUTTIME;
        if(!redisLock.lock(productId, String.valueOf(time))){
            throw new SellException(101, "请重试");
        }

        int stockNum = stock.get(productId);
        if(stockNum == 0){
            throw new SellException(100, "活动结束");
        } else {
            order.put(KeyUtils.genUniqueKey(), productId);
            stockNum = stockNum-1;
            try{
                Thread.sleep(100);
            } catch (Exception e){
                e.printStackTrace();
            }
            stock.put(productId, stockNum);
        }

        redisLock.unLock(productId, String.valueOf(time));
    }

}
