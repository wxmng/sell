package com.imooc.service.impl;

import com.imooc.dto.OrderDTO;
import com.imooc.service.PushMessage;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author 王兴明
 * @date 2019/5/31 21:38
 */
@Service
@Slf4j
public class PushMessageImpl implements PushMessage {

    @Autowired
    WxMpService wxMpService;

    @Override
    public void orderStatus(OrderDTO orderDto) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId("plIbR3ISyNYCtwx8hf5UBFMXYT8bLBfyDv8Cl67sBz4");
        wxMpTemplateMessage.setToUser("ojpl81GRjxu5m42RDcbd0T8k9_FI");
        List<WxMpTemplateData> list = Arrays.asList(
                new WxMpTemplateData("first", "亲，请记得收货。"),
                new WxMpTemplateData("keyword1", "微信点餐"),
                new WxMpTemplateData("keyword2", "18868812345"),
                new WxMpTemplateData("keyword3", orderDto.getOrderId()),
                new WxMpTemplateData("keyword4", orderDto.getOrderStatusEnum().getMsg()),
                new WxMpTemplateData("keyword5", "￥" + orderDto.getOrderAmount()),
                new WxMpTemplateData("remark", "欢迎再次光临！"));
        wxMpTemplateMessage.setData(list);
        try{
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch(WxErrorException e){
            log.error("【微信模版消息】发送失败, {}", e);
        }
    }
}
