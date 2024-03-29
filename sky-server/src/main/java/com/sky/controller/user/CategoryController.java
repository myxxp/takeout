package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user/category")
@RestController("userCategoryController")
@Slf4j
@Api(tags = "用户分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    @ApiOperation(value = "获取分类列表")
    public Result<List<Category>> getByType(Integer type){
        log.info("根据分类类型查询分类：{}", type);
        List<Category> category = categoryService.getByType(type);
        return Result.success(category);
    }

}
