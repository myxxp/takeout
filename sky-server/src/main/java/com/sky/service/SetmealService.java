package com.sky.service;

import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.dto.SetmealDTO;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * 添加套餐
     * @param setmealDTO
     */
    void addSetmeal(SetmealDTO setmealDTO);



    /**
     * 分页查询套餐
     * @param setmealPageQueryDTO
     * @return
     */

    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);


    /**
     * 更新套餐状态
     * @param id
     * @param status
     */

    void updateSetmealStatus(Long id, Integer status);

    /**
     * 批量删除套餐
     * @param ids
     */
    void deleteSetmeal(List<Long> ids);

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    SetmealVO selectSetmealById(Long id);

    /**
     * 更新套餐
     * @param setmealDTO
     */
    void updateSetmeal(SetmealDTO setmealDTO);


    /**
     * 根据分类id查询套餐
     * @param categoryId
     * @return
     */
    List<Setmeal> getByCategory(Setmeal setmeal);

    List<DishItemVO> getSetmealDish(Long id);
}
