package cn.yoozki.campussupport.order.service;

import cn.yoozki.campussupport.order.pojo.OrderDO;

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
    Long insertOrderDelivery(String deliveryOpenId, OrderDO orderDO);

}
