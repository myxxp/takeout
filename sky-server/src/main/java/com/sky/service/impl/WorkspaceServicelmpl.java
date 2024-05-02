package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WorkspaceServicelmpl implements WorkspaceService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
        Map map = new HashMap();
        map.put("begin",begin);
        map.put("end",end);


        //查询总订单数
        Integer totalOrderCount = orderMapper.countByMap(map);

        map.put("status", Orders.COMPLETED);
        //营业额

        Double turnover = orderMapper.sumByMap(map);

        turnover = turnover == null? 0.0 : turnover;

        //有效订单
        Integer validOrderCount = orderMapper.countByMap(map);

        //平均客单价

        Double unitPrice = 0.0;

        Double orderCompletionRate = 0.0;
        if(totalOrderCount !=0 && validOrderCount !=0){
            orderCompletionRate = (double)validOrderCount.doubleValue() / totalOrderCount;
            unitPrice = turnover / validOrderCount;
        }

        //新增用户
        Integer newUserCount = userMapper.countByMap(map);

        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .unitPrice(unitPrice)
                .orderCompletionRate(orderCompletionRate)
                .newUsers(newUserCount)
                .build();
    }

    @Override
    public OrderOverViewVO getOrderOverView() {
        Map map = new HashMap();
        map.put("status", Orders.COMPLETED);
        map.put("begin", LocalDateTime.now().with(LocalDateTime.MIN));

        //待接单
        Integer waitAcceptOrderCount = orderMapper.countByMap(map);

        //待配送

        map.put("status", Orders.CONFIRMED);
        Integer waitDeliverOrderCount = orderMapper.countByMap(map);

//        //待收货
//
//        map.put("status", Orders.DELIVERED);
//        Integer waitReceiveOrderCount = orderMapper.countByMap(map);

        //已完成
        map.put("status", Orders.COMPLETED);
        Integer completedOrderCount = orderMapper.countByMap(map);

        //已取消
        map.put("status", Orders.CANCELLED);
        Integer canceledOrderCount = orderMapper.countByMap(map);

        //总订单数
        map.put("status", null);
        Integer totalOrderCount = orderMapper.countByMap(map);

        return OrderOverViewVO.builder()
                .waitingOrders(waitAcceptOrderCount)
                .deliveredOrders(waitDeliverOrderCount)
                .completedOrders(completedOrderCount)
                .cancelledOrders(canceledOrderCount)
                .allOrders(totalOrderCount)
                .build();


    }

    /**
     * 查询菜品总览
     * @return
     */

    @Override
    public DishOverViewVO getDishOverView() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = dishMapper.countByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = dishMapper.countByMap(map);

        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    /**
     * 查询套餐总览
     * @return
     */
    @Override
    public SetmealOverViewVO getSetmealOverView() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = setmealMapper.countByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = setmealMapper.countByMap(map);

        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

}
