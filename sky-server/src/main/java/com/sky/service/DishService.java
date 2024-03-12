package com.sky.service;

import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DishService {

    /**
     * 添加菜品
     * @param dishDTO
     */
    void addDish(DishDTO dishDTO);

    /**
     * 分页查询菜品
     * @param dishPageQueryDTO
     * @return
     */
    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品
     * @param ids
     */
    void deleateDish(List<Long> ids);

    /**
     * 更新菜品状态
     * @param status
     * @param id
     */

    void updataDishStatus(Integer status, long id);

    /**
     * 修改菜品
     * @param dishDTO
     */
    void updateDish(DishDTO dishDTO);

    /**
     * 根据菜品id查询菜品
     * @param id
     * @return
     */
    Dish getByDishId(Long id);

    List<Dish> getByCategory(Long categoryId);
}
