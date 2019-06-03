package com.imooc.dataobject.mapper;

import com.imooc.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.Map;

/**
 * @author 王兴明
 * @date 2019/6/1 20:44
 */
public interface ProductCategoryMapper {

        @Insert("insert into product_category(category_name, category_type) values (#{category_name, jdbcType=VARCHAR}, #{category_type,jdbcType=INTEGER})")
        int insert(Map<String, Object> map);

        @Select("select * from product_category where category_type = #{categoryType}")
        @Results({
                @Result(column = "category_Type", property = "categoryType"),
                @Result(column = "category_Id", property = "categoryId"),
                @Result(column = "category_Name", property = "categoryName")
        })
        ProductCategory selectByCategory(Integer categoryType);

    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    int update(@Param("categoryName") String categoryName,
               @Param("categoryType") Integer categoryType);

    @Delete("delete from product_category where category_type = #{categoryType}")
    int delete(Integer categoryType);
    }
