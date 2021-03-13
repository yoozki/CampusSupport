package cn.yoozki.campussupport.order.service.impl;

import cn.yoozki.campussupport.order.common.OrderConst;
import cn.yoozki.campussupport.order.mapper.OrderDeliveryMapper;
import cn.yoozki.campussupport.order.mapper.OrderMapper;
import cn.yoozki.campussupport.order.pojo.OrderDO;
import cn.yoozki.campussupport.order.pojo.OrderDeliveryDO;
import cn.yoozki.campussupport.order.service.OrderDeliveryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author yoozki
 * @date 2021/3/12
 */
@Service
public class OrderDeliveryServiceImpl implements OrderDeliveryService {

    @Autowired
    private OrderDeliveryMapper orderDeliveryMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Long insertOrderDeliveryDO(String deliveryOpenId, OrderDO orderDO) {
        OrderDeliveryDO orderDeliveryDO = new OrderDeliveryDO();
        orderDeliveryDO.setOrderId(orderDO.getOrderId());
        orderDeliveryDO.setDeliveryOpenId(deliveryOpenId);
        orderDeliveryDO.setReceiverOpenId(orderDO.getReceiverOpenId());
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            code.append(new Random().nextInt(10));
        }
        orderDeliveryDO.setCode(Integer.parseInt(code.toString()));
        orderDeliveryMapper.insert(orderDeliveryDO);
        UpdateWrapper<OrderDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", OrderConst.PROCESS_STATUS);
        orderMapper.update(orderDO, updateWrapper);
        return orderDO.getOrderId();
    }

    @Override
    public OrderDeliveryDO getOrderDeliveryDO(Long orderId) {
        QueryWrapper<OrderDeliveryDO> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId);
        return orderDeliveryMapper.selectOne(wrapper);
    }



}
