package com.imooc.service;

import com.imooc.dataobject.ProductCategory;

import java.util.List;

/**
 * @author 王兴明
 * @date 2019/5/12 16:25
 */
public interface CategoryService {
    public ProductCategory findOne(int categoryId);
    public List<ProductCategory> findAll();
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);
    public ProductCategory save(ProductCategory productCategory);
}
