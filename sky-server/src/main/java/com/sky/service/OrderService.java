package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import org.springframework.stereotype.Service;


public interface OrderService {

    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);


    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * 用户端订单分页查询
     * @param page
     * @param pageSize
     * @param status
     * @return
     */
//    OrderVO historyOrder(Integer page, Integer pageSize, Integer status);

    /**
     * 查询历史订单
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult page(OrdersPageQueryDTO ordersPageQueryDTO);
    /**
     * 查询订单详情
     * @param id
     * @return
     */

    OrderVO details(Long id);
    /**
     * 用户取消订单
     * @param id
     */
    void userCancelById(Long id) throws Exception;

    /**
     * 再来一单
     * @param id
     */
    void repetition(Long id);

    PageResult page_admin(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO statistics();

    OrderVO getDetailById(Long id);

    /**
     * 接单
     *
     * @param id
     */
    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    void rejection(OrdersRejectionDTO ordersRejectionDTO);

    void delivery(Long id);

    void complete(Long id);

    void reminder(Long id);
}
