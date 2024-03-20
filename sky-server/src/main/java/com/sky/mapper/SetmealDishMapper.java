package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
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


    void addSetmealDish(List<SetmealDish> Dishes);

    /**
     * 删除套餐关联菜品
     * @param id
     */

    void deleteSetmealDish(Long id);
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> selectSetmealDishBySetmealId(Long setmealId);
}
