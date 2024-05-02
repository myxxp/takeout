package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface SetmealMapper {

    /**
     * 新增套餐
     * @param setmeal
     */
    @AutoFill(value = OperationType.INSERT)
    void addSetmeal(Setmeal setmeal);

    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);


    /**
     * 更新套餐状态
     * @param setmeal
     */
    @Update("update setmeal set status = #{status} where id = #{id}")
    void updateSetmealStatus(Setmeal setmeal);

    /**
     * 根据id查询套餐
     * @param id
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal selectSetmealById(Long id);

    /**
     * 更新套餐
     * @param setmeal
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

    /**
     * 删除套餐
     * @param id
     */
    @Delete("delete from setmeal where id = #{id}")
    void deleteSetmeal(Long id);

    /**
     * 根据分类id查询套餐
     * @param categoryId
     * @return
     */

    List<Setmeal> getByCategory(Setmeal setmeal);

    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> selectDishBySetmealId(Long id);

    /**
     * 根据条件统计套餐数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);

    /**
     * 根据分类id统计套餐数量
     * @param id
     * @return
     */

    @Select("select count(*) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);
}
