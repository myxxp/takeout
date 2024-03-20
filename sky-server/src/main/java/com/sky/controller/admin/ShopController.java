package com.sky.controller.admin;


import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@Slf4j
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
public class ShopController {

    public static final String KEY = "SHOP_STATUS";
    @Autowired

    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation(value = "设置店铺状态")
    public Result setShopStatus(@PathVariable Integer status) {

        log.info("设置店铺状态：status={}", status ==1 ? "营业中" : "休息中");

        redisTemplate.opsForValue().set(KEY, status);

        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation(value = "获取店铺状态")

    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("获取店铺状态:{}", status == 1 ? "营业中" : "休息中");
        return Result.success(status);
    }
}
