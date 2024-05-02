package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CategoryMapper {


    /**
     * 添加分类
     * @param category
     */
    @Insert("insert into category(name, status, create_time, update_time, create_user, update_user, type) " +
            "values" +
            "(#{name}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser}, #{type})")
    @AutoFill(value = OperationType.INSERT)
    void addCategory(Category category);

    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */

    //Category getByType(int type);
    List<Category>  getByType(Integer type);

    void deleteCategory(Long id);

    void updateCategoryStatus(Integer status, long id);
    /**
     * 更新分类
     * @param category
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateCategory(Category category);
}
