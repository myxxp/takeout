package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
     /**
      * 添加分类
      * @param categoryDTO
      */
     void addCategory(CategoryDTO categoryDTO);

     /**
      * 分页查询分类
      * @param categoryPageQueryDTO
      * @return
      */
     PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);
     /**
      * 根据分类类型查询分类
      * @return
      */
     List<Category> getByType(Integer type);

     void deleteCategory(int id);

     void updateCategoryStatus(Integer status, long id);

     void updateCategory(CategoryDTO categoryDTO);
}
