package com.sky.controller.admin;



import com.sky.dto.SetmealDTO;

import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;

import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "套餐相关接口")

public class SetmealConroller {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    @ApiOperation(value = "添加套餐")
    public  Result addSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("添加套餐：{}", setmealDTO);
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询套餐")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("分页查询套餐：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.page(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")

    public Result updateSetmelStatus(@PathVariable Integer status, long id){
        log.info("更新套餐状态：id={}, status={}", id, status);
        setmealService.updateSetmealStatus(id, status);
        return Result.success();
    }
    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "批量删除套餐")

    public Result deleteSetmeal(@RequestParam List<Long> ids){
        log.info("删除套餐：{}", ids);
        setmealService.deleteSetmeal(ids);
        return Result.success();
    }
    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<SetmealVO> selectSetmealById(@PathVariable Long id){
        log.info("查询套餐：{}", id);
        SetmealVO setmealVO = setmealService.selectSetmealById(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation(value = "更新套餐")
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("更新套餐：{}", setmealDTO);
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }

}
