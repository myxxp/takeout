package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sky.service.CategoryService;

import java.util.List;

@RequestMapping("/admin/category")
@RestController
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {


    @Autowired
    //TODO ？Autowired注解的用法还不了解
    private CategoryService categoryService;
    /**
     * 添加分类
     * @return
     */
    @PostMapping
    @ApiOperation(value = "添加分类")
    public Result addCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("添加分类：{}", categoryDTO);
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }
    /**
     * 分页查询分类  3-13 修复分类型查询BUG，在分页查询上添加了分类类型查询
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询分类")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {

        log.info("分页查询分类：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.page(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据分类类型查询分类
     * @return
     * 3-13 修复BUG crab
     */
    @GetMapping("/list")
    @ApiOperation(value = "根据分类类型查询分类")

    public  Result<List<Category>> getByType(Integer type){
        log.info("根据分类类型查询分类：{}", type);
        List<Category> category = categoryService.getByType(type);
        return Result.success(category);
    }

    /**
     * 删除分类
     * @return
     */
    //
    //@TODO day3 公共字段自动填充还没写
    @DeleteMapping()
    @ApiOperation(value = "删除分类")
    public Result deleteCategory(Long id){
        log.info("删除分类：{}", id);
        categoryService.deleteCategory(id);
        return Result.success();
    }

    /**
     * 修改分类状态
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "修改分类状态")
    public Result updateCategoryStatus(@PathVariable Integer status,long id) {
        log.info("修改分类状态：id={}, status={}", id, status);
        categoryService.updateCategoryStatus(status, id);

        return Result.success();
    }


    /**
     * 修改分类信息
     * @param categoryDTO
     * @return
     */
    @PutMapping()
    @ApiOperation(value = "修改分类信息")

    public Result  updateCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("修改分类信息：{}", categoryDTO);
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }
}
