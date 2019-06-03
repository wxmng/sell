package com.imooc.service;

import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author 王兴明
 * @date 2019/5/13 11:22
 */
public interface ProductService {
    //
    public ProductInfo findOne(String produceId);

    public List<ProductInfo> findUpAll();

    public Page<ProductInfo> findAll(Pageable pageable);

    public ProductInfo save(ProductInfo productInfo);

    //扣库存
    public void decreaseStock(List<CartDTO> cartDTOList);

    //加库存
    public void increaseStock(List<CartDTO> cartDTOList);

    //上架
    public void on_sale(String productId);

    //下架
    public void off_sale(String productId);
}
