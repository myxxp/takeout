package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishMapper {


    void addDish(Dish dish);
    /**
     * 分页查询菜品
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 获取菜品主键id
     * @param id
     */
    @Select("select * from dish where id = #{id}")
    Dish selectDishById(Long id);



    /**
     * 删除菜品
     * @param id
     */
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    /**
     * 更新菜品状态
     * @param status
     * @param id
     */
    @Update("update dish set status = #{status} where id = #{id}")
    void updataDishStatus(Integer status, long id);

    void updateDish(Dish dish);

    @Select("select * from dish where category_id = #{categoryId}")
    List<Dish> selectByCategory(Long categoryId);


    @Select("select * from dish where id in (select dish_id from setmeal_dish where setmeal_id = #{id})")
    List<Dish> selectDishBySetmealId(Long id);

    /**
     * 动态条件查询菜品
     *
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);



}
