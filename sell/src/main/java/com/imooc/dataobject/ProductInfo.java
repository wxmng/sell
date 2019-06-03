package com.imooc.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.enums.ProductInfoStatus;
import com.imooc.utils.EnumUtils;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 王兴明
 * @date 2019/5/12 17:17
 */
@Entity
@Data
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = -1486019932021066004L;
    @Id
    private String productId;

    /** 名字. */
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 状态, 0正常1下架. */
    private Integer productStatus = ProductInfoStatus.UP.getCode();

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductInfoStatus getProductInfoStatus(){
        return EnumUtils.getByCode(this.productStatus, ProductInfoStatus.class);
    }


}
