package com.imooc.service.impl;

import com.imooc.dao.ProductInfoDao;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import com.imooc.enums.ProductInfoStatus;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author 王兴明
 * @date 2019/5/13 11:30
 */
@Service
@CacheConfig(cacheNames = "product")
public class ProduceServiceImpl implements ProductService {

    @Autowired
    ProductInfoDao productInfoDao;

    @Override
    @Cacheable(key = "123")
    public ProductInfo findOne(String produceId) {
        return productInfoDao.findOne(produceId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoDao.findByProductStatus(ProductInfoStatus.UP.getProduceStatus());
    }

    @Override
    //定义一个页面请求信息
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDao.findAll(pageable);
    }

    @Override
    @CachePut(key = "123")
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoDao.save(productInfo);
    }

    @Override
    //减库存
    public void decreaseStock(List<CartDTO> cartDTOList) {

        for(CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = productInfoDao.findOne(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoDao.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = productInfoDao.findOne(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoDao.save(productInfo);
        }
    }

    @Override
    public void on_sale(String productId) {
        ProductInfo productInfo = productInfoDao.findOne(productId);
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductInfoStatus() == ProductInfoStatus.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductInfoStatus.UP.getProduceStatus());
        productInfoDao.save(productInfo);
    }

    @Override
    public void off_sale(String productId) {
        ProductInfo productInfo = productInfoDao.findOne(productId);
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductInfoStatus() == ProductInfoStatus.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductInfoStatus.DOWN.getProduceStatus());
        productInfoDao.save(productInfo);
    }
}
