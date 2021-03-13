package cn.yoozki.campussupport.order.service.impl;

import cn.yoozki.campussupport.order.common.OrderConst;
import cn.yoozki.campussupport.order.mapper.OrderMapper;
import cn.yoozki.campussupport.order.message.OrderDelayChannel;
import cn.yoozki.campussupport.order.pojo.OrderDO;
import cn.yoozki.campussupport.order.pojo.dto.OrderInsertDTO;
import cn.yoozki.campussupport.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author yoozki
 * @date 2021/2/10
 */
@Service
@EnableBinding(OrderDelayChannel.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDelayChannel orderDelayChannel;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Long insertOrder(OrderInsertDTO orderInsertDTO, String receiverOpenId) {
        OrderDO orderDO = packOrderDO(orderInsertDTO, receiverOpenId);
        orderMapper.insert(orderDO);
        Long orderId = orderDO.getOrderId();
        Message<Long> message = MessageBuilder.withPayload(orderId).
                setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, "4")
                .build();
        orderDelayChannel.output().send(message);
        return orderId;
    }

    @Override
    public OrderDO getOrder(Long orderId) {
        QueryWrapper<OrderDO> wrapper = new QueryWrapper<OrderDO>().eq("order_id", orderId);
        return orderMapper.selectOne(wrapper);
    }

    @Override
    public Long updateOrder(OrderDO orderDO, Integer status) {
        orderDO.setStatus(status);
        orderDO.setGmtModified(new Date());
        orderMapper.updateById(orderDO);
        return orderDO.getOrderId();
    }

    @StreamListener(value = OrderDelayChannel.INPUT)
    public void orderListener(Message<Long> message) {
        Long orderId = message.getPayload();
        OrderDO orderDO = getOrder(orderId);
        Integer status = orderDO.getStatus();
        if (status.equals(OrderConst.UNPAID_STATUS)) {
            updateOrder(orderDO, OrderConst.CANCEL_STATUS);
        }
    }

    private OrderDO packOrderDO(OrderInsertDTO orderInsertDTO, String receiverOpenId) {
        String targetAddress = orderInsertDTO.getTargetAddress();
        String deliveryAddress = orderInsertDTO.getDeliveryAddress();
        String title = orderInsertDTO.getTitle();
        String detail = orderInsertDTO.getDetail();
        BigDecimal payCost = orderInsertDTO.getPayCost();
        String receiverName = orderInsertDTO.getReceiverName();
        String receiverPhone = orderInsertDTO.getReceiverPhone();
        BigDecimal reward = orderInsertDTO.getReward();
        Date deliveryTime = orderInsertDTO.getDeliveryTime();
        Integer sexLimit = orderInsertDTO.getSexLimit();
        Integer tagId = orderInsertDTO.getTagId();
        // 封装OrderDO
        OrderDO orderDO = new OrderDO();
        orderDO.setReceiverOpenId(receiverOpenId);
        orderDO.setTargetAddress(targetAddress);
        orderDO.setDeliveryAddress(deliveryAddress);
        orderDO.setTitle(title);
        orderDO.setDetail(detail);
        orderDO.setPayCost(payCost);
        orderDO.setReceiverName(receiverName);
        orderDO.setReceiverPhone(receiverPhone);
        orderDO.setReward(reward);
        orderDO.setDeliveryTime(deliveryTime);
        orderDO.setSexLimit(sexLimit);
        orderDO.setTagId(tagId);
        orderDO.setStatus(OrderConst.UNPAID_STATUS);
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyMMddHHmmss");
        String temp = simpleDateFormat.format(new Date());
        for (int i = 0; i < 4; i++) {
            temp += new Random().nextInt(10);
        }
        Long orderId = Long.parseLong(temp);
        orderDO.setOrderId(orderId);
        Date date = new Date();
        orderDO.setGmtCreate(date);
        orderDO.setGmtModified(date);
        return orderDO;
    }
}
