package cn.yoozki.campussupport.order.service;

import cn.yoozki.campussupport.order.pojo.OrderDO;
import cn.yoozki.campussupport.order.pojo.OrderDeliveryDO;

/**
 * @author yoozki
 * @date 2021/3/12
 */
public interface OrderDeliveryService {

    /**
     * 往 order_delivery 表中插入数据
     * @param deliveryOpenId
     * @param orderDO
     * @return
     */
    Long insertOrderDeliveryDO(String deliveryOpenId, OrderDO orderDO);

    /**
     * 根据 orderId 查询 OrderDeliveryDO 对象
     * @param orderId
     * @return
     */
    OrderDeliveryDO getOrderDeliveryDO(Long orderId);

}
