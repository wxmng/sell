package com.imooc.dto;

import lombok.Data;

/**
 * @author 王兴明
 * @date 2019/5/15 11:55
 */
@Data
public class CartDTO {
    private String productId;
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
