package com.sky.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sky.entity.Category;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServicelmpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 添加分类
     * @param categoryDTO
     */
    public void addCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setStatus(StatusConstant.ENABLE);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.addCategory(category);



    }
    /**
     * 分页查询分类
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {

        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);

        long total = page.getTotal();
        List<Category> result = page.getResult();
        return new PageResult(total, result);

    }
    /**
     * 根据分类类型查询分类
     * @param type
     * @return
     */
    public List<Category> getByType(int type) {

        List<Category> category = categoryMapper.getByType(type);
        return category;

    }

    /**
     * 删除分类
     * @param id
     */
    public void deleteCategory(int id) {
        categoryMapper.deleteCategory(id);
    }
    /**
     * 修改分类状态
     * @param status
     * @param id
     */

    public void updateCategoryStatus(Integer status, long id) {
        categoryMapper.updateCategoryStatus(status, id);

    }

    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.updateCategory(category);
    }


}
