package com.imooc.controller;

import com.imooc.config.ProjectUrlConfig;
import com.imooc.constants.CookieConstant;
import com.imooc.constants.RedisConstant;
import com.imooc.dataobject.SellerInfo;
import com.imooc.enums.ResultEnum;
import com.imooc.service.impl.SellerServiceImpl;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 王兴明
 * @date 2019/5/31 15:09
 */
@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {

    @Autowired
    private SellerServiceImpl sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map){
        //openid在数据库中对比
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenId(openid);
        if(sellerInfo == null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("/common/error", map);
        }
        //设置token至redis
        String token = KeyUtils.genUniqueKey();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        //设置token至cookie
        CookieUtils.set(response, CookieConstant.TOKEN, token, expire);
        //返回跳转，跳转的时候一定要用完整的地址不要用相对地址
        log.info("redict", projectUrlConfig.getSell());
        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletResponse response,
                               HttpServletRequest request,
                               Map<String, Object> map){
        //查询cookie
        Cookie cookie = CookieUtils.get(request, CookieConstant.TOKEN);
        if(cookie != null){
            //清除redis中的cookie
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            CookieUtils.set(response, CookieConstant.TOKEN, null, 0);
        }
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "order/list");
        return new ModelAndView("/common/success", map);
    }
}
