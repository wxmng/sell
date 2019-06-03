package com.imooc.service.impl;

import com.imooc.dao.CategoryDao;
import com.imooc.dataobject.ProductCategory;
import com.imooc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王兴明
 * @date 2019/5/12 16:31
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Override
    public ProductCategory findOne(int categoryId) {
        return categoryDao.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType) {
        return categoryDao.findByCategoryTypeIn(categoryType);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return categoryDao.save(productCategory);
    }
}
