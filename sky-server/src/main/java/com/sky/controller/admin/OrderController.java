package com.sky.controller.admin;

import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController("adminOrderContro")
@RequestMapping("/admin/order")
@Slf4j
@Api(tags = "订单管理接口")
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 查询套餐
     * @param ordersPageQueryDTO
     * @return
     */
    @GetMapping("/conditionSearch")
    @ApiOperation("分页查询套餐")
    public Result<PageResult> page(OrdersPageQueryDTO ordersPageQueryDTO){
        log.info("分页查询订单", ordersPageQueryDTO);
        PageResult pageResult = orderService.page_admin(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 订单统计
     * @return
     */
    @GetMapping("/statistics")
    @ApiOperation("订单统计")
    public Result statistic() {
        OrderStatisticsVO orderStatisticsVO = orderService.statistics();
        return Result.success(orderStatisticsVO);
    }

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    @GetMapping("/details/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> order_detail(@PathVariable Long id) {
        OrderVO orderVO = orderService.getDetailById(id);

        return Result.success(orderVO);

    }
    //TODO 方法优化，不直接在mapper层修改状态为固定值。并使用通用update方法。并比较两者区别写blog

    /**
     * 接单
     * @param ordersConfirmDTO
     * @return
     */
    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {

        orderService.confirm(ordersConfirmDTO);
        return Result.success();

    }

    /**
     * 拒单
     * @param ordersRejectionDTO
     * @return
     */
    @PutMapping("/rejection")
    @ApiOperation("拒单")
    public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) {
        log.info("拒单 ");
        orderService.rejection(ordersRejectionDTO);
        return Result.success();
    }

    /**
     * 派送订单
     * @param id
     * @return
     */
    @PutMapping("/delivery/{id}")
    public Result delivery(@PathVariable Long id) {
        log.info("派送订单");
        orderService.delivery(id);
        return Result.success();


    }
    @PutMapping("/complete/{id}")
    public Result complete(@PathVariable Long id) {
        orderService.complete(id);
        return Result.success();
    }
}
