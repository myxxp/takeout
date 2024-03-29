package com.sky.mapper;


import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {



    /**
     * 添加菜品口味
     * @param flavors
     */
    //TODO 添加口味XML语法还没搞清楚
    void addDishFlavor(List<DishFlavor> flavors);

    @Delete("delete from dish_flavor where dish_id = #{dishid}")
    void deleteByDishId(Long dishid);


    /**
     * 根据菜品id查询对应的口味数据
     * @param dishId
     * @return
     */
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);


}
