package com.imooc.handler;

import com.imooc.VO.ResultVO;
import com.imooc.config.ProjectUrlConfig;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.exception.SellerAuthorizeException;
import com.imooc.utils.ResultVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 王兴明
 * @date 2019/5/31 20:10
 */
//进行跳转
@ControllerAdvice
@Slf4j
public class SellExceptionHandler {
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @ExceptionHandler(value = SellerAuthorizeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handlerAuthorizeException(){
        String url = "redirect:"
                .concat(projectUrlConfig.wechatOpenAuthorize)
                .concat("/sell/wechat/qrAuthorize?")
                .concat("returnUrl=")
                .concat(projectUrlConfig.getSell())
                .concat("sell/seller/login");
            log.info("跳转url", url);
        return new ModelAndView(url);
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handerSellerException(SellException e){
        return ResultVOUtils.error(e.getCode(), e.getMessage());
    }
}
