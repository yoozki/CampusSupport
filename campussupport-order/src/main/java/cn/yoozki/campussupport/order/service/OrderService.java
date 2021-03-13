package cn.yoozki.campussupport.order.service;

import cn.yoozki.campussupport.order.pojo.OrderDO;
import cn.yoozki.campussupport.order.pojo.dto.OrderInsertDTO;

/**
 * @author yoozki
 * @date 2021/2/10
 */
public interface OrderService {

    /**
     * 插入订单数据到表中
     * @param orderInsertDTO
     * @param receiverOpenId
     * @return 订单id
     */
    Long insertOrder(OrderInsertDTO orderInsertDTO, String receiverOpenId);

    /**
     * 根据订单号获取订单详情
     * @param orderId
     * @return orderDO
     */
    OrderDO getOrder(Long orderId);

    /**
     * 更改订单状态
     * @param orderDO
     * @param status
     * @return
     */
    Long updateOrder(OrderDO orderDO, Integer status);


}
