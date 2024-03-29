package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.sky.result.Result;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")

public class DishConroller {

    @Autowired
    private DishService dishService;

    @Autowired

    private RedisTemplate redisTemplate;



    /**
     * 添加菜品
     * @return
     */
    @PostMapping
    @ApiOperation(value = "添加菜品")
    public  Result addDish(@RequestBody DishDTO dishDTO){
        log.info("添加菜品：{}", dishDTO);
        dishService.addDish(dishDTO);


        //清理缓存数据
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);
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

        //所有菜品缓存数据清理掉
        cleanCache("dish_*");

        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation(value = "更新菜品状态")
    public Result updateDishStatus(@PathVariable Integer status, long id){
        log.info("更新菜品状态：id={}, status={}", id,status);
        dishService.updataDishStatus(status, id);

        //清理缓存数据
        cleanCache("dish_*");
        return Result.success();
    }


    @PutMapping
    @ApiOperation(value = "修改菜品")
    public Result updateDish(@RequestBody DishDTO dishDTO){
        log.info("更新菜品：{}", dishDTO);
        dishService.updateDish(dishDTO);

        //清理缓存数据
        cleanCache("dish_*");
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询菜品")
    public Result<DishVO> getByDishId(@PathVariable Long id){
        log.info("根据id查询菜品：{}", id);

        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    @GetMapping("/list")
    @ApiOperation(value = "根据分类查询菜品")
    public Result<List<Dish>> getByCategory(Long categoryId){
        log.info("根据分类查询菜品：{}", categoryId);
        List<Dish> dish = dishService.getByCategory(categoryId);
        return Result.success(dish);
    }

    /**
     * 清理缓存数据
     * @param pattern
     */
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

}
