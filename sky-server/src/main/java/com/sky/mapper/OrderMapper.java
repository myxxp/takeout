package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
         /**
          * 插入订单
          * @param orders
          */
        void insert(Orders orders);

         /**
          * 根据订单号查询订单
          * @param orderNumber
          */
         @Select("select * from orders where number = #{orderNumber}")
         Orders getByNumber(String orderNumber);

         /**
          * 修改订单信息
          * @param orders
          */
         void update(Orders orders);
         /**
          * 分页查询订单
          * @param ordersPageQueryDTO
          * @return
          */
         Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

         /**
          * 根据id查询订单
          * @param id
          * @return
          */
         @Select("select * from orders where id = #{id}")
         Orders getById(Long id);

      /**
       * 根据订单id查询订单详情
       * @param id
       * @return
       */
      @Select("select * from order_detail where order_id = #{orderId}")
      List<OrderDetail> getByOrderId(Long id);

      Integer countStatus(Integer toBeConfirmed);


      void confirm(Long id);

      /**
       * 根据订单状态和下单时间查询订单
       * @param status
       * @param orderTime
       * @return
       */
      @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
      List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

      /**
       * 根据动态条件统计营业额数据
       * @param map
       * @return
       */
      Double sumByMap(Map map);

       /**
       * 根据动态条件统计订单数量
       * @param map
       * @return
       */
      Integer countByMap(Map map);

    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);
}
