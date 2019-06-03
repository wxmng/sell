package com.imooc.aspect;

import com.imooc.constants.CookieConstant;
import com.imooc.constants.RedisConstant;
import com.imooc.exception.SellerAuthorizeException;
import com.imooc.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 王兴明
 * @date 2019/5/31 19:31
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.imooc.controller.Seller*.*(..))" +
    "&& !execution(public * com.imooc.controller.SellerUserController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Cookie cookie = CookieUtils.get(request, CookieConstant.TOKEN);
        //cookie中查找token
        if(cookie == null){
            log.warn("[登录错误]cookie中查找不到token");
            throw new SellerAuthorizeException();
        }
        //在redis中查找token
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if(tokenValue == null){
            log.warn("[登录错误]redis中找不到token");
            throw new SellerAuthorizeException();
        }
    }
}
