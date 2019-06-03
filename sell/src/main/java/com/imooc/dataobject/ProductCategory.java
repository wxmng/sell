package com.imooc.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.util.Date;

/**
 * @author 王兴明
 * @date 2019/5/12 13:09
 */
//把普通的Java bean映射到数据库中
@Entity
//加该注解可以自动更新时间
@DynamicUpdate
//该注解配合lombok插件可以省区写get set toString方法
@Data
public class ProductCategory {

    //类目ID为主键，并且为自增
    @Id
    @GeneratedValue
    private Integer categoryId;
    //类目名a
    private String categoryName;
    //类目编号
    private Integer categoryType;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    public ProductCategory() {
    }
    public ProductCategory(String category_name, Integer category_type){
        this.categoryName = category_name;
        this.categoryType = category_type;
    }

}
