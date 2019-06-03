package com.imooc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 王兴明
 * @date 2019/5/30 22:03
 */
@Data
@ConfigurationProperties(prefix = "projectUrl")
@Component
public class ProjectUrlConfig {
    /**
     * 微信公众平台授权url
     */
    public String wechatMpAuthorize ="http://sellto.natapp1.cc";

    /**
     * 微信开放平台授权url
     */
    public String wechatOpenAuthorize ="http://sellto.natapp1.cc";

    /**
     * 点餐系统
     */
    public String sell = "http://sellto.natapp1.cc";
}
