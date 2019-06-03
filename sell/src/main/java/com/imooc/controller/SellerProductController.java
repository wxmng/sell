package com.imooc.controller;

import com.imooc.dataobject.ProductCategory;
import com.imooc.dataobject.ProductInfo;
import com.imooc.exception.SellException;
import com.imooc.form.ProductForm;
import com.imooc.service.ProductService;
import com.imooc.service.impl.CategoryServiceImpl;
import com.imooc.service.impl.ProduceServiceImpl;
import com.imooc.utils.KeyUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author 王兴明
 * @date 2019/5/29 16:38
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {
    @Autowired
    ProduceServiceImpl produceService;

    @Autowired
    CategoryServiceImpl categoryService;

    @GetMapping("list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = produceService.findAll(pageRequest);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/product/list", map);
    }

    @GetMapping("on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map){
        try{
            produceService.on_sale(productId);
        } catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("/common/success", map);
    }

    @GetMapping("off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String, Object> map){
        try{
            produceService.off_sale(productId);
        } catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("/common/success", map);
    }

    @GetMapping("index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map){
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = produceService.findOne(productId);
            map.put("productInfo", productInfo);
        }
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("product/index", map);
    }

    @PostMapping("save")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("/common/error", map);
        }
        ProductInfo productInfo = new ProductInfo();
        try{
            if(!StringUtils.isEmpty(form.getProductId())){
                productInfo = produceService.findOne(form.getProductId());
            }else {
                form.setProductId(KeyUtils.genUniqueKey());
            }
            BeanUtils.copyProperties(form, productInfo);
            produceService.save(productInfo);
        } catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("/common/error", map);
        }

        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("/common/success", map);
    }
}
