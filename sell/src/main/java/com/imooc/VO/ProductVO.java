package com.imooc.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imooc.dataobject.ProductInfo;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 *
 *商品包括类目
 * @author 王兴明
 * @date 2019/5/13 16:08
 */
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 7097863777546530545L;
    @JsonProperty("name")
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
