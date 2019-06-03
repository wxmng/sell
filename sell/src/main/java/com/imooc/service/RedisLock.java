package com.imooc.service;

import com.imooc.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.core.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author 王兴明
 * @date 2019/6/3 19:21
 */
@Commit
@Slf4j
@Service
public class RedisLock {

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 参数key: productId  value：当前时间加超时时间
     * @return
     */
    public boolean lock(String key, String value){
        if(redisTemplate.opsForValue().setIfAbsent(key, value)){
            return true;
        }

        //操作过程出现异常该做的操作  上个锁的第一步出成功， 该步锁的第一步没有成功
        String currentTime = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(currentTime) && Long.parseLong(currentTime)
        < System.currentTimeMillis()){
            //把新的锁设置上并且取得上一个锁的时间  该方法一次只能有一个线程执行
            //其中一个拿到oldTime以后把key 设置为新的value
            String oldTime = redisTemplate.opsForValue().getAndSet(key, value);
            if(!StringUtils.isEmpty(currentTime) && oldTime.equals(currentTime)){
                return true;
            }
        }
        return false;
    }
    /**
     * 解锁
     */
    public void unLock(String key, String value) {
        try {
            String currentTime = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentTime) && currentTime.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            log.error("解锁异常", e);
        }
    }
}