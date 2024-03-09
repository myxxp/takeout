package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
     * @param ids
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
}
