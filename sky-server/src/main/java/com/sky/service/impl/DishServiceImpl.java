package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {


    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;


    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 添加菜品
     * @param dishDTO
     */

    public void addDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setStatus(StatusConstant.ENABLE);
        dish.setCreateTime(LocalDateTime.now());
        dish.setUpdateTime(LocalDateTime.now());
        dish.setCreateUser(BaseContext.getCurrentId());
        dish.setUpdateUser(BaseContext.getCurrentId());

        dishMapper.addDish(dish);

        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
        dishFlavorMapper.addDishFlavor(flavors);

        }

    }
    /**
     * 分页查询菜品
     * @param dishPageQueryDTO
     * @return
     */
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult( page.getTotal(), page.getResult());

    }

    /**
     * 批量删除菜品
     * @param ids
     */

    public void deleateDish(List<Long> ids) {

        //判段是否在起售中

        for (Long id : ids) {
            Dish dish = dishMapper.selectDishById(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                throw new RuntimeException(MessageConstant.DISH_ON_SALE);
            }
        }


        //判断是否关联套餐

        List<Long> setmealIDs = setmealDishMapper.getSetmealIdishIdsByDishIds(ids);
        if(setmealIDs != null &&  setmealIDs.size() > 0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);

        }

        //删除菜品中的菜品数据
        for (Long id : ids) {

            dishMapper.deleteById(id);

            //删除关联的口味
            dishFlavorMapper.deleteByDishId(id);

        }
    }
    public void updataDishStatus(Integer status, long id) {
        dishMapper.updataDishStatus(status, id);
    }

    /**
     * 修改菜品
     * @param dishDTO
     */
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setUpdateTime(LocalDateTime.now());
        dish.setUpdateUser(BaseContext.getCurrentId());
        dishMapper.updateDish(dish);

        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            dishFlavorMapper.deleteByDishId(dishId);
            dishFlavorMapper.addDishFlavor(flavors);
        }
    }

    /**
     * 根据菜品id查询菜品
     * @param id
     * @return
     */

    public Dish getByDishId(Long id) {

        return dishMapper.selectDishById(id);
    }
    /**
     * 根据id查询菜品和对应的口味数据
     * @param id
     * @return
     */
    public DishVO getByIdWithFlavor(Long id) {
        Dish dish = dishMapper.selectDishById(id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    public List<Dish> getByCategory(Long categoryId) {
        return dishMapper.selectByCategory(categoryId);
    }

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }

}
