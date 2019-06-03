package com.imooc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 王兴明
 * @date 2019/5/22 15:41
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    private String mpAppId;

    private String mpAppSecert;

    /**
     * 开放平台id
     */
    private String openAppId;

    /**
     * 开放平台密钥
     */
    private String openAppSecret;


    //商户号
    private String mchId;

    //商户密匙
    private String mchKey;

    //商户证书路径
    private String keyPath;

    //异步通知地址
    private String notifyUrl;
}
