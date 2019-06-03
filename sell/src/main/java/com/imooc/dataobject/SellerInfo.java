package com.imooc.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author 王兴明
 * @date 2019/5/30 20:10
 */
@Entity
@Data
public class SellerInfo {

    @Id
    private String sellerId;

    private String userName;

    private String passWord;

    private String openId;

}
