package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SetmealMapper {


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
    void updateSetmeal(Setmeal setmeal);

    /**
     * 删除套餐
     * @param id
     */
    @Delete("delete from setmeal where id = #{id}")
    void deleteSetmeal(Long id);
}
