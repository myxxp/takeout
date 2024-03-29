package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class SetmealServiceImpl implements SetmealService {


    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private DishMapper dishMapper;

    /**
     * 添加套餐
     *
     * @param setmealDTO
     */

    public void addSetmeal(SetmealDTO setmealDTO) {

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setStatus(StatusConstant.ENABLE);
        setmeal.setCreateTime(LocalDateTime.now());
        setmeal.setUpdateTime(LocalDateTime.now());
        setmeal.setCreateUser(BaseContext.getCurrentId());
        setmeal.setUpdateUser(BaseContext.getCurrentId());

        setmealMapper.addSetmeal(setmeal);

        Long setmealId = setmeal.getId();
        List<SetmealDish> Dishes = setmealDTO.getSetmealDishes();
        if (Dishes != null && Dishes.size() > 0) {
            Dishes.forEach(Dish -> {

                Dish.setSetmealId(setmealId);

            });
        }
        setmealDishMapper.addSetmealDish(Dishes);
    }


    /**
     * 分页查询套餐
     *
     * @param setmealPageQueryDTO
     * @return
     */
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {

        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);

//        long total = page.getTotal();
//        List<SetmealVO> result = page.getResult();
//        return new PageResult(total, result);
        //修改代码 修复返回结果不显示套餐名称
        return new PageResult(page.getTotal(), page.getResult());
    }
    /**
     * 更新套餐状态
     *
     * @param id
     * @param status
     */

    public void updateSetmealStatus(Long id, Integer status) {
//        Setmeal setmeal = new Setmeal();
//        setmeal.setId(id);
//        setmeal.setStatus(status);
//        setmeal.setUpdateTime(LocalDateTime.now());
//        setmeal.setUpdateUser(BaseContext.getCurrentId());
//        setmealMapper.updateSetmealStatus(setmeal);
        //修改代码
        if(Objects.equals(status, StatusConstant.ENABLE)){
            List<Dish> dishList = dishMapper.selectDishBySetmealId(id);
            if(dishList != null && dishList.size() > 0){
                dishList.forEach(dish -> {
                    if(Objects.equals(dish.getStatus(), StatusConstant.DISABLE)){
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                });

            }

        }
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        setmealMapper.updateSetmealStatus(setmeal);
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     */

    public void deleteSetmeal(List<Long> ids) {
        ids.forEach(id -> {
            Setmeal setmeal = new Setmeal();
            if(Objects.equals(setmeal.getStatus(), StatusConstant.ENABLE)){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }

            else {
                setmealMapper.deleteSetmeal(id);
                setmealDishMapper.deleteSetmealDish(id);
            }



        });
    }
    /**
     * 根据id查询套餐和套餐餐品关系
     *
     * @param id
     * @return
     */
    public SetmealVO selectSetmealById(Long id) {
        Setmeal setmeal = setmealMapper.selectSetmealById(id);
        List<SetmealDish> setmealDishes = setmealDishMapper.selectSetmealDishBySetmealId(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /**
     * 更新套餐
     *
     * @param setmealDTO
     */

    public void updateSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setUpdateTime(LocalDateTime.now());
        setmeal.setUpdateUser(BaseContext.getCurrentId());
        setmealMapper.updateSetmeal(setmeal);


        //参考黑马答案完善代码
        //套餐id
        Long setmealId = setmeal.getId();
        //删除套餐关联菜品
        setmealDishMapper.deleteSetmealDish(setmealId);
        //添加套餐关联菜品
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && setmealDishes.size() > 0) {
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealId);
            });
            setmealDishMapper.addSetmealDish(setmealDishes);
        }


    }
    /**
     * 根据分类id查询套餐
     *
     * @param categoryId
     * @return
     */
    public List<Setmeal> getByCategory(Setmeal setmeal) {
        List<Setmeal> setmeals = setmealMapper.getByCategory(setmeal);
        return setmeals;
    }

    public List<DishItemVO> getSetmealDish(Long id) {
        return setmealMapper.selectDishBySetmealId(id);
    }
}

