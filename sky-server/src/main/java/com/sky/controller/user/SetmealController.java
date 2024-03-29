package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Slf4j
@Api(tags = "套餐相关接口")

public class SetmealController {


    @Autowired
    private SetmealService setmealService;
    /**
     * 获取套餐列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取套餐列表")
    @Cacheable(cacheNames = "setmeal", key = "#categoryId")
    public Result<List<Setmeal>> getSetmealByCategory(Long categoryId) {
      log.info("获取套餐列表:{}", categoryId);
      Setmeal setmeal = new Setmeal();
      setmeal.setCategoryId(categoryId);
      setmeal.setStatus(StatusConstant.ENABLE);
      List<Setmeal> setmeals = setmealService.getByCategory(setmeal);
      return Result.success(setmeals);
   }

        /**
        * 根据套餐Id查询菜品列表
        */
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "根据套餐Id查询菜品列表")
   public Result<List<DishItemVO>> getSetmealDish(@PathVariable Long id) {
      log.info("获取套餐详情:{}", id);
      List<DishItemVO> dishItemVO = setmealService.getSetmealDish(id);
      return Result.success(dishItemVO);
   }


}
