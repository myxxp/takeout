package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface SetmealDishMapper {


//    /**
//     * 根据菜品id查询对应的套餐
//     * @param setmealId
//     * @param dishIds
//     */

    List<Long> getSetmealIdishIdsByDishIds(List<Long> dishIds);



}
