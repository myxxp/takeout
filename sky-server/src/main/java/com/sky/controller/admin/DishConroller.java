package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.sky.result.Result;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")

public class DishConroller {

    @Autowired
    private DishService dishService;

    /**
     * 添加菜品
     * @return
     */
    @PostMapping
    @ApiOperation(value = "添加菜品")
    public  Result addDish(@RequestBody DishDTO dishDTO){
        log.info("添加菜品：{}", dishDTO);
        dishService.addDish(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询菜品")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询菜品：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);

    }


    @DeleteMapping
    @ApiOperation(value = "批量删除菜品")

    public Result deleateDish(@RequestParam List<Long> ids){
        log.info("删除菜品：{}", ids);
        dishService.deleateDish(ids);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation(value = "更新菜品状态")
    public Result updateDishStatus(@PathVariable Integer status, long id){
        log.info("更新菜品状态：id={}, status={}", id,status);
        dishService.updataDishStatus(status, id);
        return Result.success();
    }


}
